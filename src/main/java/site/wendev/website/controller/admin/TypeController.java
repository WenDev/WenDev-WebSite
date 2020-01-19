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
import site.wendev.website.entities.Type;
import site.wendev.website.service.TypeService;

@Controller
@RequestMapping("/admin/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping(value = {"", "/add"})
    public String typeManagePage(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.ASC)
                                             Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "type_manage";
    }

    @PostMapping("/add")
    public String addType(Type type, RedirectAttributes attributes) {
        // 查找有无同名分类
        var type1 = typeService.findByName(type);
        if (type1 != null) {
            attributes.addFlashAttribute("errInfo", "添加失败：已经有同名分类存在");
            return "redirect:/admin/type";
        }

        var result = typeService.addType(type);
        if (result == null) {
            attributes.addFlashAttribute("errInfo", "添加失败：系统异常");
        } else {
            attributes.addFlashAttribute("successInfo", "添加成功");
        }

        return "redirect:/admin/type";
    }

    @GetMapping("/edit/{id}")
    public String editTypePage(@PathVariable Long id, Model model) {
        var type = typeService.getType(id);
        if (type == null) {
            model.addAttribute("errInfo", "要编辑的类型不存在");
        } else {
            model.addAttribute("type", type);
        }

        return "type_manage";
    }

    /**
     * 根据id修改一个类型
     *
     * TODO: 现在修改不存在的Type是直接出异常，不能返回错误信息，需要改进，问题可能出在DAO层或者Service层
     */
    @PostMapping("/edit/{id}")
    public String editType(@PathVariable Long id, Type type, RedirectAttributes attributes) {
        // 查找有无同名分类
        var type1 = typeService.findByName(type);
        if (type1 != null) {
            attributes.addFlashAttribute("errInfo", "修改失败：已经有同名分类存在");
            return "redirect:/admin/type/edit/" + id;
        }

        // 没有同名分类，可以修改
        var result = typeService.modify(id, type.getName());
        if (result == null) {
            attributes.addFlashAttribute("errInfo", "修改失败：系统异常");
        }

        return "redirect:/admin/type";
    }

    /**
     * 根据id删除一个类型
     *
     * TODO: 现在删除不存在的Type是直接出异常，不能返回错误信息，需要改进，问题可能出在DAO层或者Service层
     */
    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        var type = typeService.getType(id);
        if (type == null) {
            attributes.addFlashAttribute("errInfo", "要删除的类型不存在");
        } else {
            typeService.deleteType(id);
        }

        return "redirect:/admin/type";
    }
}
