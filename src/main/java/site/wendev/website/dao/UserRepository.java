package site.wendev.website.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wendev.website.entities.User;

/**
 * 用户相关DAO操作
 *
 * @author jiangwen
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名和密码查询出对应的用户，在登录中使用
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询成功为对应用户，否则为null
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * 用于在注册中判断用户名是否有被使用
     *
     * @param username 用户名
     * @return 查询成功为对应用户，否则为null
     */
    User findByUsername(String username);
}
