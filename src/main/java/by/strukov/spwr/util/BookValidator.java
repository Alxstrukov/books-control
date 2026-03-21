package by.strukov.spwr.util;

import by.strukov.spwr.dao.BookDAO;
import by.strukov.spwr.dao.PersonDAO;
import by.strukov.spwr.model.Book;
import by.strukov.spwr.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book updatedBook = (Book) target;
        if (bookDAO.isFullEquals(updatedBook))
            errors.rejectValue("title", "",
                    "Книга с названием: "
                            + updatedBook.getTitle() + ", автором: "
                            + updatedBook.getAuthor() + ", годом издания: "
                            + updatedBook.getYear() + " - уже существует!");
    }


}
