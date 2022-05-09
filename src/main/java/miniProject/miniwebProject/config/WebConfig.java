package miniProject.miniwebProject.config;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.config.auth.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 직접 커스텀하게 만든 LoginUserArgumentResolver를 사용하려면 등록해야된다.
 * HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolvers를 통해 추가해야 한다.
 * 이 WebMvcConfigurer를 이용하면 dispatcherServlet을 원하는대로 커스텀 가능해진다.
 */

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}
