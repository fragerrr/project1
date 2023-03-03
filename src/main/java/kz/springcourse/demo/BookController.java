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
        model.addAttribute("book", DBManager.getBook(id));
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
    public String update(@PathVariable(name = "id") Integer id, @ModelAttribute @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "book/edit";
        if(DBManager.updateUser(person, id)){
            return "redirect:/book";
        } else{
            return "error";
        }
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id){
        if(DBManager.deleteUser(id)){
            return "redirect:/book";
        } else{
            return "error";
        }
    }
}
