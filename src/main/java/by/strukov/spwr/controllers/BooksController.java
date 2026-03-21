package by.strukov.spwr.controllers;

import by.strukov.spwr.dao.BookDAO;
import by.strukov.spwr.model.Book;
import by.strukov.spwr.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String showBooks(Model model) {
        model.addAttribute("books", bookDAO.getListAllBooks());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Validated Book newBook, BindingResult bindingResult) {

        validator.validate(newBook, bindingResult);

        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.create(newBook);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") @Validated Book updatedBook, BindingResult bindingResult) {
        updatedBook.setBook_id(id);
        validator.validate(updatedBook, bindingResult);
        if (bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(updatedBook);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
