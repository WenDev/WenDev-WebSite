package site.wendev.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主要页面的Controller
 *
 * @author 江文
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }
}
