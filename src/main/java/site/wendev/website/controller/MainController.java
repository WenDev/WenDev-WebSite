package site.wendev.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.wendev.website.entities.User;
import site.wendev.website.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 主要Controller，负责大多数不需要权限的页面、业务逻辑
 *
 * @author jiangwen
 */
@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        RedirectAttributes attributes) {
        User user = userService.login(username, password);

        // 登录成功，user != null
        if (user != null) {
            // 添加session
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            return "redirect:/";
        } else {
            attributes.addFlashAttribute("errInfo", "用户名或密码错误");
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam @Valid String username, @RequestParam @Valid String password,
                           @RequestParam @Valid String repeatPassword, @RequestParam @Valid String email,
                           @RequestParam @Valid String nickname, RedirectAttributes attributes) {
        // 重复密码与原密码不相同
        if (!password.equals(repeatPassword)) {
            attributes.addFlashAttribute("errInfo", "重复密码与原密码不相同");
            return "redirect:/register";
        }

        // 注册新用户
        var user = userService.register(new User().setUsername(username).setPassword(password)
                .setEmail(email).setNickname(nickname));

        // user == null，用户名已被使用
        if (user == null) {
            attributes.addFlashAttribute("errInfo", "用户名已被使用");
            return "redirect:/register";
        } else {
            // 注册成功
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("userId");
        session.removeAttribute("username");
        session.removeAttribute("role");
        session.invalidate();

        return "redirect:/";
    }
}
