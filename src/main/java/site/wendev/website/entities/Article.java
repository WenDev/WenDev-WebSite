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
 * 文章实体类
 *
 * @author jiangwen
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "wendev_article")
public class Article {
    /**
     * 主键，值为自动生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文章标题
     */
    @NotNull(message = "标题不能为空")
    @NotEmpty(message = "标题不能为空字符串")
    @Column
    private String title;

    /**
     * 文章内容，因为太长所以映射为TEXT类型
     */
    @NotNull(message = "文章内容不能为空")
    @NotEmpty(message = "文章内容不能为空字符串")
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 文章阅读量
     */
    @Column
    private Integer views;

    /**
     * 是否已发布
     * 这其实是一个保留字段，目前还用不到
     */
    @Column
    private Boolean published;

    /**
     * 是否开启评论功能
     */
    @Column
    private Boolean commentEnabled;

    /**
     * 文章的创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 文章的更新时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 该文章所属的类型
     */
    @ManyToOne
    private Type type;

    /**
     * 该文章所拥有的标签，设置级联新增
     */
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();

    /**
     * 该文章对应的评论
     */
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    /**
     * 乐观锁
     */
    @Version
    private Long version;
}
