package site.wendev.website.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 *
 * @author jiangwen
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "wendev_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空字符串")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Column
    @NotNull(message = "用户昵称不能为空")
    @NotEmpty(message = "用户昵称不能为空字符串")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @Column
    @NotNull(message = "邮箱不能为空")
    @NotEmpty(message = "邮箱不能为空字符串")
    private String email;

    @Column
    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空字符串")
    private String password;

    /**
     * 用户权限
     * 这个字段是为了以后可能对接Spring Security保留的
     */
    @Column
    @NotNull(message = "用户角色不能为空")
    @NotEmpty(message = "用户角色不能为空字符串")
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 该用户所发表的全部评论
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Version
    private Long version;
}
