package site.wendev.website.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiangwen
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "wendev_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 该评论的发表者
     */
    @ManyToOne
    private User user;

    /**
     * 该评论所属于的文章
     */
    @ManyToOne
    private Article article;

    /**
     * 该评论下的子评论
     */
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> replyComments = new ArrayList<>();

    /**
     * 该评论的父评论
     */
    @ManyToOne
    private Comment parentComment;

    @Column
    @NotNull(message = "评论内容不能为空")
    @NotEmpty(message = "评论内容不能为空字符串")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Version
    private Long version;
}
