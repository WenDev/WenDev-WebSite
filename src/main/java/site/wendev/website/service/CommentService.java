package site.wendev.website.service;

import site.wendev.website.entities.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listByArticleId(Long articleId);
    Comment add(Comment comment);
    void delete(Long id);
}
