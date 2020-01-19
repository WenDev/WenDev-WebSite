package site.wendev.website.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.wendev.website.entities.Tag;
import site.wendev.website.service.TagService;

@Controller
@RequestMapping("/admin/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping(value = {"", "/add"})
    public String tagManagePage(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.ASC)
                                         Pageable pageable, Model model) {
        // 添加标签列表数据
        model.addAttribute("page", tagService.listTag(pageable));
        return "tag_manage";
    }

    @PostMapping("/add")
    public String add(Tag tag, RedirectAttributes attributes) {
        // 查找有无同名标签
        var tag1 = tagService.findByName(tag);
        if (tag1 != null) {
            // 存在同名标签
            attributes.addFlashAttribute("errInfo", "添加失败：已有同名标签存在");
            return "redirect:/admin/tag";
        }

        // 添加标签
        var result = tagService.add(tag);
        if (result == null) {
            // result == null，添加失败
            attributes.addFlashAttribute("errInfo", "添加失败：系统异常");
        } else {
            // 添加成功
            attributes.addFlashAttribute("successInfo", "添加成功");
        }

        return "redirect:/admin/tag";
    }

    /**
     * 根据id返回修改标签的页面
     *
     * TODO: 现在修改不存在的标签直接500，不能返回错误信息，问题可能出在thymeleaf的某个表达式
     */
    @GetMapping("/edit/{id}")
    public String editTagPage(@PathVariable Long id, Model model) {
        // 参数合法性校验：查找该id的tag是否存在
        var tag = tagService.findById(id);
        if (tag == null) {
            model.addAttribute("errInfo", "要编辑的标签不存在");
        } else {
            model.addAttribute("tag", tag);
        }

        return "tag_manage";
    }

    @PostMapping("/edit/{id}")
    public String editTag(@PathVariable Long id, Tag tag, RedirectAttributes attributes) {
        // 查找有无同名标签
        var tag1 = tagService.findByName(tag);
        if (tag1 != null) {
            attributes.addFlashAttribute("errInfo", "修改失败：已有同名分类存在");
            return "redirect:/admin/tag/edit/" + id;
        }

        // 不存在同名标签，可以修改
        var result = tagService.modify(id, tag);
        if (result == null) {
            attributes.addFlashAttribute("errInfo", "修改失败：系统异常");
        }

        return "redirect:/admin/tag";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        var tag = tagService.findById(id);
        if (tag == null) {
            attributes.addFlashAttribute("errInfo", "删除失败：要删除的类型不存在");
        } else {
            tagService.delete(id);
        }

        return "redirect:/admin/tag";
    }
}
