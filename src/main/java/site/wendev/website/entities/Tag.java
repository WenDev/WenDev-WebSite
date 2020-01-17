package site.wendev.website.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章标签实体类
 *
 * @author jiangwen
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "wendev_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 拥有该tag的文章列表
     * 关系的维护端
     */
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    /**
     * 标签名称
     */
    @Column
    @NotNull(message = "标签名称不能为空")
    @NotEmpty(message = "标签类型不能为空")
    private String name;

    @Version
    private Long version;
}
