package onion.whnazv.biblioteca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private int stock;
    private String imageUrl;
    private String description;
}
