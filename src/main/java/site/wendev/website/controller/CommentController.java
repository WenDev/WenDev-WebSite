package site.wendev.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import site.wendev.website.entities.Comment;
import site.wendev.website.service.ArticleService;
import site.wendev.website.service.CommentService;
import site.wendev.website.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/comments/{id}")
    public String post(Comment comment, @PathVariable Long id, Long parentId) {
        Long userId = (Long) session.getAttribute("userId");
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        comment.setParentComment(new Comment().setId(parentId));
        comment.setArticle(articleService.findById(id));
        comment.setUser(userService.findById(userId));
        comment.setCreateTime(new Date());
        commentService.add(comment);
        return "redirect:/article/" + comment.getArticle().getId();
    }

    @GetMapping("/comments/delete/{articleId}/{id}")
    public String delete(@PathVariable Long id, @PathVariable Long articleId) {
        String role = (String) session.getAttribute("role");
        if ("ROLE_ADMIN".equals(role)) {
            commentService.delete(id);
        }
        return "redirect:/article/" + articleId;
    }
}
