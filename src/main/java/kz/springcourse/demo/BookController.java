package kz.springcourse.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class BookController {
    @GetMapping()
    public String getBooks(Model model){
        model.addAttribute("books", DBManager.getBooks());
        return "book/index";
    }
}
