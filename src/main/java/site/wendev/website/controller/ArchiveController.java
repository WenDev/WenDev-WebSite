package site.wendev.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.wendev.website.service.ArticleService;

@Controller
@RequestMapping("/archive")
public class ArchiveController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public String archivePage(Model model) {
        model.addAttribute("archiveMap", articleService.archiveArticle());
        return "archive";
    }
}
