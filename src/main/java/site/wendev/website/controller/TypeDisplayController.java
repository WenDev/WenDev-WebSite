package site.wendev.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.wendev.website.service.ArticleService;
import site.wendev.website.service.TypeService;
import site.wendev.website.vo.ArticleVO;

@Controller
@RequestMapping("/type")
public class TypeDisplayController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = {"/{id}", ""})
    public String typeDisplayPage(@PathVariable(required = false) Long id,
                                  @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                          Pageable pageable, Model model) {
        var types = typeService.listType(100000);
        // 如果没有传id参数，就默认显示最多的那一项
        if (id == null) {
            id = types.get(0).getId();
        }

        // 完成类型列表与文章的分页查询
        model.addAttribute("types", types);
        model.addAttribute("page", articleService.list(pageable, new ArticleVO().setTypeId(id)));
        model.addAttribute("activeTypeId", id);

        return "type";
    }
}
