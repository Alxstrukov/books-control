package by.strukov.spwr.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private Integer person_id;//очень ВАЖНО чтобы имя поля совпадало с именем столбца в БД

    @NotEmpty(message = "Поле не может быть пустым")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+",
            message = "ФИО должно быть указано кириллицей в формате: Иванов Иван Иванович")
    private String fullName;

    @NotNull(message = "Вы не указали дату рождения")
    @Min(value = 1900, message = "Год не может быть ранее 1900")
    private Integer birthDate;
}
