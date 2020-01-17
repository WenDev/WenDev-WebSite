package site.wendev.website.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @GetMapping("/article")
    public String articleManagePage() {
        return "article_manage";
    }

    @GetMapping("/user")
    public String userManagePage() {
        return "user_manage";
    }
}
