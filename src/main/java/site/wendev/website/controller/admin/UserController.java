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
import site.wendev.website.entities.User;
import site.wendev.website.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列出所有用户的数据，按照降序排序
     */
    @GetMapping(value = {"", "/add"})
    public String userManagePage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                        Pageable pageable, Model model) {
        // 添加用户列表数据
        model.addAttribute("page", userService.list(pageable));
        return "user_manage";
    }

    @GetMapping("/edit/{id}")
    public String editUserPage(@PathVariable Long id, Model model) {
        var user = userService.findById(id);
        if (user == null) {
            model.addAttribute("errInfo", "要编辑的用户不存在");
        } else {
            model.addAttribute("user", user);
        }

        return "user_manage";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, User user, RedirectAttributes attributes) {
        var user1 = userService.findByUsername(user);
        if (user1 != null && !user.getId().equals(user1.getId())) {
            attributes.addFlashAttribute("errInfo", "编辑失败：已存在同名用户");
            return "redirect:/admin/user/edit/" + id;
        }

        var result = userService.modify(id, user);
        if (result == null) {
            attributes.addFlashAttribute("errInfo", "编辑失败：系统异常");
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        var user = userService.findById(id);
        if (user == null) {
            attributes.addFlashAttribute("errInfo", "删除失败：要删除的用户不存在");
        } else {
            userService.delete(id);
        }

        return "redirect:/admin/user";
    }
}
