package by.strukov.spwr.controllers;

import by.strukov.spwr.dao.BookDAO;
import by.strukov.spwr.model.Book;
import by.strukov.spwr.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final BookValidator validator;

    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator validator) {
        this.bookDAO = bookDAO;
        this.validator = validator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.getListAllBooks());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Validated Book book, BindingResult bindingResult) {

        validator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.create(book);
        return "redirect:/books";
    }

}
