package site.wendev.website.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import site.wendev.website.entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long id, Sort sort);
    List<Comment> findByArticleIdAndParentCommentNull(Long id, Sort sort);
}
