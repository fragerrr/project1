package kz.springcourse.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/book")
public class BookController {
    @GetMapping()
    public String main(Model model){
        model.addAttribute("books", DBManager.getBooks());

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model){
        Book book = DBManager.getBook(id);
        model.addAttribute("book", book);

        Person person = DBManager.getUser(book.getTook_user());

        if(person == null){
            model.addAttribute("took", false);
        } else{
            model.addAttribute("took", true);
            model.addAttribute("person", person);
        }


        model.addAttribute("people", DBManager.getUsers());
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
        if(DBManager.addBook(book)){
            return "redirect:/book";
        } else{
            return "error";
        }

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("book", DBManager.getBook(id));
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable(name = "id") Integer id, @ModelAttribute @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "book/edit";
        if(DBManager.updateBook(book, id)){
            return "redirect:/book";
        } else{
            return "error";
        }
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id){
        if(DBManager.deleteBook(id)){
            return "redirect:/book";
        } else{
            return "error";
        }
    }

    @PutMapping("/{id}/take")
    public String take(@PathVariable(name = "id") Integer id, @ModelAttribute("book") Book book){
        if(DBManager.updateTookUser(id, book.getTook_user())){
            return "redirect:/book";
        } else{
            return "error";
        }
    }

    @PatchMapping("/{id}/take")
    public String take(@PathVariable(name = "id") Integer id){
        if(DBManager.deleteTookUser(id)){
            return "redirect:/book";
        } else{
            return "error";
        }
    }
}
