package site.wendev.website.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wendev.website.dao.CommentRepository;
import site.wendev.website.entities.Comment;
import site.wendev.website.exception.NotFoundException;
import site.wendev.website.service.CommentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listByArticleId(Long articleId) {
        var order = new Sort.Order(Sort.Direction.DESC, "createTime");
        var comments = commentRepository.findByArticleIdAndParentCommentNull(articleId, Sort.by(order));
        return listTopComments(comments);
    }

    @Override
    @Transactional
    public Comment add(Comment comment) {
        var parentComment = comment.getParentComment();
        if (parentComment != null) {
            if (parentComment.getId() != null) {
                var parentCommentId = parentComment.getId();
                var parentCommentOptional = commentRepository.findById(parentCommentId);
                if (parentCommentOptional.isEmpty()) {
                    throw new RuntimeException("根据ParentCommentId得到的对象为空");
                }

                comment.setParentComment(parentCommentOptional.get());
                comment.setCreateTime(new Date());
            } else {
                comment.setParentComment(null);
            }
        } else {
            comment.setParentComment(null);
        }
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new NotFoundException("找不到id为" + id + "的评论");
        } else {
            var comment = commentOptional.get();
            comment.setParentComment(null);
            var comment1 = save(comment);
            commentRepository.deleteById(comment1.getId());
        }
    }

    private Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    private List<Comment> listTopComments(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (var comment : comments) {
            var c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }

        // 合并所有子评论至同一级
        combineChild(commentsView);
        return commentsView;
    }

    /// 临时存放查出的所有子评论
    private List<Comment> temp = new ArrayList<>();

    private void combineChild(List<Comment> comments) {
        for (var comment : comments) {
            List<Comment> replys = comment.getReplyComments();
            for (var reply : replys) {
                r(reply);
            }
            comment.setReplyComments(temp);

            // 清空临时存放列表
            temp = new ArrayList<>();
        }
    }

    // 递归找出所有子评论
    private void r(Comment comment) {
        // 顶点添加到集合
        temp.add(comment);
        if (comment.getReplyComments().size() > 0) {
            var replys = comment.getReplyComments();
            for (var reply : replys) {
                temp.add(reply);

                // 递归添加子评论
                if (reply.getReplyComments().size() > 0) {
                    r(reply);
                }
            }
        }
    }
}
