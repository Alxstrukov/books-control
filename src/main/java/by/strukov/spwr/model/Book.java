package by.strukov.spwr.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Укажите год издания книги")
    @Min(value = 0, message = "Год не может быть отрицательным")
    private Integer year;
}
