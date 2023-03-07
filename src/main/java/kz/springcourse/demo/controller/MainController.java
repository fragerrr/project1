package kz.springcourse.demo.controller;

import jakarta.validation.Valid;
import kz.springcourse.demo.model.Book;
import kz.springcourse.demo.model.Person;

import kz.springcourse.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class MainController {


    private final PersonService personService;

    @Autowired
    public MainController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String main(Model model){
        model.addAttribute("people", personService.getUsers());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model){
        Person person = personService.getUser(id);
        model.addAttribute("person", person);

        List<Book> books = person.getBooks();
        if(books.isEmpty()){
            model.addAttribute("took", true);
        } else{
            model.addAttribute("books", books);
        }


        return "show";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("person") Person person){
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "new";
        personService.addUser(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("person", personService.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable(name = "id") Integer id, @ModelAttribute @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "edit";
        personService.updateUser(person, id);
        return "redirect:/people";

    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id){
        personService.deleteUser(id);
        return "redirect:/people";
    }

}
