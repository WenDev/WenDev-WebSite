package site.wendev.website.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.wendev.website.entities.Article;
import site.wendev.website.service.ArticleService;
import site.wendev.website.vo.ArticleVO;


@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = {""})
    public String articleManagementPage(Model model, ArticleVO article,
                                        @PageableDefault(size = 2, sort = {"updateTime"},
                                                direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", articleService.list(pageable, article));
        return "article_manage";
    }

    @PostMapping(value = {"/search"})
    public String articleManagementPageSearch(Model model, ArticleVO article,
                                        @PageableDefault(size = 2, sort = {"updateTime"},
                                                direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", articleService.list(pageable, article));
        return "article_manage::articleList";
    }
}
