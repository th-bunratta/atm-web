package th.ac.ku.atm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home2")
    public String getHomePage(Model model) {
        model.addAttribute("greeting", "Sawasdee");
        return "home";
    }
}
