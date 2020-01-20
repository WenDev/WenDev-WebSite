package site.wendev.website.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.wendev.website.entities.Article;
import site.wendev.website.entities.Tag;
import site.wendev.website.entities.Type;
import site.wendev.website.service.ArticleService;
import site.wendev.website.service.TagService;
import site.wendev.website.service.TypeService;
import site.wendev.website.vo.ArticleVO;

import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    // 字符串的分割串，去掉字符串所有空格
    public static final String BLANK = "\\s+";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

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

    @GetMapping("/add")
    public String addArticlePage(Model model) {
        model.addAttribute("article", new Article());
        return "add";
    }

    @PostMapping(value = {"/add", "/edit/{id}"})
    public String addArticle(@RequestParam @Valid String title, @RequestParam @Valid String content,
                             @RequestParam @Valid String type, @RequestParam @Valid String tag,
                             RedirectAttributes attributes, @PathVariable Long id) {
        // 根据空格切分type，得到type数组并转换为List<Tag>类型
        var tagsNameArray = tag.trim().split(BLANK);
        List<Tag> tagList = new ArrayList<>();
        for (String item : tagsNameArray) {
            var simpleTag = tagService.findByName(new Tag().setName(item));

            if (simpleTag != null) {
                tagList.add(simpleTag);
            }
        }

        // 得到Type类型
        var articleType = typeService.findByName(new Type().setName(type));


        if (id == null) {
            // 建立一个新的Article对象并传值保存
            var article = new Article().setTitle(title).setContent(content)
                    .setType(articleType).setTags(tagList).setCreateTime(new Date())
                    .setUpdateTime(new Date()).setViews(0).setPublished(true).setCommentEnabled(true);

            var result = articleService.add(article);
            if (result == null) {
                attributes.addFlashAttribute("errInfo", "添加失败：系统异常");
                return "redirect:/admin/article/add";
            } else {
                return "redirect:/admin/article";
            }
        } else {
            // 建立一个新的Article对象并传值保存
            var article = new Article().setTitle(title).setContent(content)
                    .setType(articleType).setTags(tagList)
                    .setUpdateTime(new Date()).setViews(0).setPublished(true).setCommentEnabled(true);

            var result = articleService.modify(id, article);
            if (result == null) {
                attributes.addFlashAttribute("errInfo", "修改失败：系统异常");
                return "redirect:/admin/article/add";
            } else {
                return "redirect:/admin/article";
            }
        }
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        var article = articleService.findById(id);

        var tagStringBuilder = new StringBuilder();
        for (var tag : article.getTags()) {
            tagStringBuilder.append(tag.getName());
            tagStringBuilder.append(" ");
        }

        model.addAttribute("article", article);
        model.addAttribute("tags", tagStringBuilder.toString());
        model.addAttribute("type", article.getType().getName());

        return "add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/admin/article";
    }
}
