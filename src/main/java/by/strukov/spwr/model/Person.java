package by.strukov.spwr.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private Long personID;

    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+}",
            message = "ФИО должно быть указано кириллицей в формате: Иванов Иван Иванович")
    private String fullName;

    @NotNull
    @Size(min = 1900)
    @Pattern(regexp = "[0-9]{4}", message = "Год рождения должен состоять из четырёх цифр и не менее 1900")
    private Integer birthDate;
}
