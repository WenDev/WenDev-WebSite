package site.wendev.website.controller.admin;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章的Controller
 * 只有管理员才可以访问
 *
 * @since 1.0.0
 * @version 1.0.1
 * @author 江文
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    /**
     * 字符串的分割串，用于分割所有的空格、空白字符等
     * 因为传入的类型是一个字符串，要把字符串转换成Tag列表，需要对字符串进行分割
     * 使用这样的匹配方法，可以减小出Bug的可能
     */
    public static final String BLANK = "\\s+";

    private final ArticleService articleService;

    private final TypeService typeService;

    private final TagService tagService;

    /**
     * 按照Spring5官方推荐，使用构造器进行依赖注入，并且弃用@Autowired注解
     * @since 1.0.1
     */
    public ArticleController(ArticleService articleService, TypeService typeService, TagService tagService) {
        this.articleService = articleService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    @GetMapping(value = {""})
    public String articleManagementPage(Model model, ArticleVO article,
                                        @PageableDefault(size = 10, sort = {"updateTime"},
                                                direction = Sort.Direction.DESC) Pageable pageable) {
        // 查询得到分页形式的文章列表数据
        model.addAttribute("page", articleService.list(pageable, article));
        return "article_manage";
    }

    @PostMapping(value = {"/search"})
    public String articleManagementPageSearch(Model model, ArticleVO article,
                                        @PageableDefault(size = 10, sort = {"updateTime"},
                                                direction = Sort.Direction.DESC) Pageable pageable) {
        // 使用搜索类 ArticleVO 的对象进行文章搜索，再返回分页的数据
        model.addAttribute("page", articleService.list(pageable, article));
        return "article_manage::articleList";
    }

    @GetMapping("/add")
    public String addArticlePage(Model model) {
        // new 一个 Article 放在页面中，方便后面增加文章时使用
        model.addAttribute("article", new Article());
        return "add";
    }

    @PostMapping(value = {"/add", "/edit/{id}"})
    public String addArticle(@RequestParam @Valid String title, @RequestParam @Valid String content,
                             @RequestParam @Valid String type, @RequestParam @Valid String tag,
                             RedirectAttributes attributes, @PathVariable(required = false) Long id) {
        // 根据空格切分tag，得到tag数组并转换为List<Tag>类型
        var tagsNameArray = tag.trim().split(BLANK);
        List<Tag> tagList = new ArrayList<>();

        // 循环遍历字符数组并且添加到tag数组中
        for (String item : tagsNameArray) {
            var simpleTag = tagService.findByName(new Tag().setName(item));

            if (simpleTag != null) {
                tagList.add(simpleTag);
            }
        }

        // 得到Type类型
        var articleType = typeService.findByName(new Type().setName(type));

        // id == null 说明是新增文章
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
            // id != null 表示修改现有文章
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

        // 根据Tag列表生成以空格切分的Tag字符串，添加到页面
        var tagStringBuilder = new StringBuilder();
        for (var tag : article.getTags()) {
            tagStringBuilder.append(tag.getName());
            tagStringBuilder.append(" ");
        }

        // 向页面中添加文章属性，然后返回add页面（add与edit页面共用）
        model.addAttribute("article", article);
        model.addAttribute("tags", tagStringBuilder.toString());
        model.addAttribute("type", article.getType().getName());

        return "add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // 直接删除对应id的文章，然后重定向到文章管理页面
        articleService.delete(id);
        return "redirect:/admin/article";
    }
}
