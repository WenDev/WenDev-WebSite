package site.wendev.website.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.wendev.website.intercepor.AdminInterceptor;

/**
 * 拦截器配置
 *
 * @author jiangwen
 */
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
    }
}
