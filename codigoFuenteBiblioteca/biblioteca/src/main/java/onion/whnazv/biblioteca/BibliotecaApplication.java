package onion.whnazv.biblioteca;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.UserEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
        System.out.println("✅ Aplicación iniciada correctamente: Swagger disponible en http://localhost:8080/swagger-ui.html");

    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Academia Almaerea")
                        .version("0.0.1")
                        .description("Documentación automática con Swagger (springdoc-openapi)"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            
            Path uploadsPath = Paths.get("uploads");
            if (!Files.exists(uploadsPath)) {
                Files.createDirectory(uploadsPath);
                System.out.println("📂 Carpeta creada: " + uploadsPath.toAbsolutePath());
            }

            
            Path imagesPath = uploadsPath.resolve("images");
            if (!Files.exists(imagesPath)) {
                Files.createDirectory(imagesPath);
                System.out.println("📂 Carpeta creada: " + imagesPath.toAbsolutePath());
            }

            
            String adminEmail = "admin33@admin.com";
            if (!userRepository.findByEmail(adminEmail).isPresent()) {
                UserEntity admin = new UserEntity();
                admin.setEmail(adminEmail);
                admin.setUsername("admin3");
                admin.setPassword(new BCryptPasswordEncoder().encode("123"));
                admin.setRole("ADMIN");
                admin.setFirstName("Admin3");
                admin.setLastName("System");
                userRepository.save(admin);

                System.out.println("✅ Usuario administrador creado: " + adminEmail);
            }
        };
    }


}
