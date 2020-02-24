package site.wendev.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import site.wendev.website.service.ArticleService;
import site.wendev.website.service.CommentService;
import site.wendev.website.service.TagService;
import site.wendev.website.service.TypeService;
import site.wendev.website.vo.ArticleVO;

/**
 * 主要页面的Controller
 *
 * @author 江文
 */
@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(Model model, ArticleVO article,
                        @PageableDefault(size = 10, sort = {"createTime"},
                                direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", articleService.list(pageable));
        model.addAttribute("types", typeService.listType(5));
        model.addAttribute("tags", tagService.listTag(10));

        return "index";
    }

    @GetMapping("/search")
    public String search(ArticleVO article, Model model, @PageableDefault(size = 10,
            sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("page", articleService.list(pageable, article));
        return "search";
    }

    @GetMapping("/article/{id}")
    public String article(@PathVariable Long id, Model model) {
        model.addAttribute("comments", commentService.listByArticleId(id));
        model.addAttribute("article", articleService.findAndConvert(id));
        return "article";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
