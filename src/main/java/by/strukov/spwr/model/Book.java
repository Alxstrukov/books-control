package by.strukov.spwr.model;


import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@Data
@ToString
public class Book {

    private int book_id;
    private Integer fkOwnerId;

    @NotEmpty(message = "Укажите название книги")
    @NotNull
    private String title;

    @NotEmpty(message = "Укажите автора")
    @NotNull
    private String author;

    @NotNull(message = "Укажите год издания книги")
    @Positive(message = "Год не может быть отрицательным")
    private Integer year;

}
