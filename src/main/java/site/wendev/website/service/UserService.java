package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.wendev.website.entities.User;

/**
 * 业务逻辑层用户相关业务逻辑
 *
 * @author jiangwen
 */
public interface UserService {
    /**
     * 登录
     *
     * @param username 要登录的用户名
     * @param password 用户密码
     * @return 登录成功为登录成功的用户，登录失败为null
     */
    User login(String username, String password);

    /**
     * 注册
     *
     * @param user 要注册的用户
     * @return 注册成功为注册成功的用户，失败为null
     */
    User register(User user);

    /**
     * 修改用户信息
     *
     * @param user 要修改信息的用户
     * @return 修改成功为修改成功的用户，修改失败为null
     */
    User modify(Long id, User user);

    /**
     * 删除用户
     *
     * @param id 要删除的用户的id
     */
    void delete(Long id);

    Page<User> list(Pageable pageable);
    User findById(Long id);
    User findByUsername(User user);
}
