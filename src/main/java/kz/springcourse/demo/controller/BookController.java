package kz.springcourse.demo.controller;

import jakarta.validation.Valid;
import kz.springcourse.demo.model.Book;
import kz.springcourse.demo.service.BookService;
import kz.springcourse.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String main(Model model){
        model.addAttribute("books", bookService.getBooks());

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model){
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);

        if(book.getTookUser() != null){
            model.addAttribute("took", true);
            model.addAttribute("person", personService.getUser(book.getTookUser()));

        } else{
            model.addAttribute("took", false);
        }


        model.addAttribute("people", personService.getUsers());
        return "book/show";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book){
        return "book/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "book/new";
        bookService.addBook(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("book", bookService.getBook(id));
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "book/edit";
        bookService.updateBook(book);
        return "redirect:/book";

    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id){

        bookService.deleteBook(id);
        return "redirect:/book";

    }

    @PutMapping("/{id}/take")
    public String take(@ModelAttribute("book") Book book){
        bookService.personTakeBook(book, book.getTookUser());
        return "redirect:/book";
    }

    @PatchMapping("/{id}/take")
    public String take(@PathVariable(name = "id") Integer id){
        bookService.getBook(id).setTookUser(null);
        return "redirect:/book";
    }
}
