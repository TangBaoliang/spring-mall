package ltd.itlover.ltd.springbootmall.config;

import ltd.itlover.ltd.springbootmall.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 宝亮
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/user/login", "/favicon.ico")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/configuration/ui")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/configuration/security")
                .excludePathPatterns("/v2/api-docs/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/**/favicon.ico")
                .excludePathPatterns("/")
                .excludePathPatterns("/csrf")
                .excludePathPatterns("/cart/carts");
    }
}
