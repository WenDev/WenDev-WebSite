package site.wendev.website.intercepor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangwen
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (!"ROLE_ADMIN".equals(request.getSession().getAttribute("role"))) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
