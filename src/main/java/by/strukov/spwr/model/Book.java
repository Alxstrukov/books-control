package by.strukov.spwr.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private Long bookID;

    @NotEmpty(message = "Укажите название книги")
    private String title;

    @NotEmpty(message = "Укажите автора")
    private String author;

    @NotNull(message = "Укажите год издания книги")
    @Positive(message = "Год не может быть отрицательным")
    private Integer year;
}
