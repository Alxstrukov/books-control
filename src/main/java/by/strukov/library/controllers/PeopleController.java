package by.strukov.library.controllers;

import by.strukov.library.dao.BookDAO;
import by.strukov.library.dao.PersonDAO;
import by.strukov.library.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String showPeople(Model model) {
        model.addAttribute("people", personDAO.getAllPeople());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int person_id, Model model) {
        model.addAttribute("person", personDAO.readByID(person_id));
        model.addAttribute("books", bookDAO.getListPersonsBooks(person_id));
        return "people/show";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.readByID(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int person_id,
                         @ModelAttribute("person") @Valid Person updatedPerson, BindingResult bindingResult) {
        updatedPerson.setPerson_id(person_id);

        if (updatedPerson.getBirthDate() > LocalDate.now().getYear())
            bindingResult.rejectValue("birthDate",
                    "error.birthDate",
                    "Год не может быть больше текущего");
        if (bindingResult.hasErrors()) return "people/edit";

        personDAO.update(person_id, updatedPerson);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person newPerson, BindingResult result) {

        if (result.hasErrors()) return "people/new";

        if (newPerson.getBirthDate() > LocalDate.now().getYear()) {
            result.rejectValue("birthDate",
                    "error.birthDate",
                    "Год не может быть больше текущего");
            return "people/new";
        }

        personDAO.create(newPerson);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person newPerson) {
        return "people/new";
    }
}
