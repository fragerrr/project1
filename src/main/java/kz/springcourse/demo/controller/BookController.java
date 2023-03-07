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

    @GetMapping("")
    public String main(Model model, @RequestParam(name = "number", required = false) Integer page,
                       @RequestParam(name = "books_per_page", required = false) Integer number_books,
                       @RequestParam(name = "sort", required = false) boolean sort){

        if(page != null && number_books != null){
            model.addAttribute("books", bookService.getBookPagination(page, number_books, sort));
        }  else{
            model.addAttribute("books", bookService.getBooks(sort));
        }


        return "book/index";
    }

    @GetMapping("/search")
    public String main(Model model, @RequestParam(name = "search", required = false) String search){

        model.addAttribute("books", bookService.getBooksBySearch(search));

        return "book/search";
    }






    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model, @ModelAttribute(name = "book1") Book book1){
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);


        model.addAttribute("people", personService.getUsers());

        System.out.println(id);


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

    @PostMapping("/{id}/take")
    public String take(@PathVariable(name = "id") Integer id, @ModelAttribute(name = "book1") Book book){

        bookService.personTakeBook(bookService.getBook(id), book.getOwner());
        return "redirect:/book";
    }

    @PatchMapping("/{id}/take")
    public String take(@PathVariable(name = "id") Integer id){
        bookService.personTakeBook(bookService.getBook(id), null);
        return "redirect:/book";
    }
}
