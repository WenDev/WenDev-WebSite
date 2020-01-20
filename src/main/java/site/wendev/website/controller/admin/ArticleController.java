package site.wendev.website.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    @GetMapping(value = {"", "/add"})
    public String articleManagementPage() {
        return "article_manage";
    }
}
