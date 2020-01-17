package site.wendev.website.service;

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
    User modify(User user);

    /**
     * 删除用户
     *
     * @param user 要删除的用户
     * @return 删除成功为0，删除失败为-1
     */
    int delete(User user);
}
