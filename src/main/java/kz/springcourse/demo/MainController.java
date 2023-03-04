package kz.springcourse.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class MainController {
    @GetMapping()
    public String main(Model model){
        model.addAttribute("people", DBManager.getUsers());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("person", DBManager.getUser(id));

        if(DBManager.getBooksByUserId(id).isEmpty()){
            model.addAttribute("took", true);
        } else{
            model.addAttribute("books", DBManager.getBooksByUserId(id));
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
        if(DBManager.addUser(person)){
            return "redirect:/people";
        } else{
            return "error";
        }

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("person", DBManager.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable(name = "id") Integer id, @ModelAttribute @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "edit";
        if(DBManager.updateUser(person, id)){
            return "redirect:/people";
        } else{
            return "error";
        }
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id){
        if(DBManager.deleteUser(id)){
            return "redirect:/people";
        } else{
            return "error";
        }
    }

}
