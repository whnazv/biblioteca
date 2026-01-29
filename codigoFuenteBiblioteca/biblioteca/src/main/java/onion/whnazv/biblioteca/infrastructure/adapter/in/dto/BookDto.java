package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;


    private String title;


    private String author;


    private String isbn;

    @NotNull
    @Positive
    private BigDecimal price;

    @PositiveOrZero
    private int stock;

    private String imageUrl;

    private String description;
}
