package kz.springcourse.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("people", DBManager.getUsers());

        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("person", DBManager.getUser(id));
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
            return "redirect:/";
        } else{
            return "error";
        }

    }

}
