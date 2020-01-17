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
 * 文章类型实体类
 *
 * @author jiangwen
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "wendev_type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型名称
     */
    @NotNull(message = "类型名称不能为空")
    @NotEmpty(message = "类型名称不能为空字符串")
    @Column
    private String name;

    /**
     * 该分类下属的文章
     * 关系的被维护端
     */
    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    @Version
    private Long version;
}
