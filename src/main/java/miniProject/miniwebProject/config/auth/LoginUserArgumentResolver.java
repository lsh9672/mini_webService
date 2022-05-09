package miniProject.miniwebProject.config.auth;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.config.auth.dto.SessionUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/**
 * HandlerMethodArgumentResolver는 조건에 맞는 메서드가 있으면, 해당 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있다.
 * 여기서는 LoginUser 어노테이션을 붙였을떄, 해당 파라미터로 세션값을 바로 넘겨주기 위해서 만든것이다.
 */

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    //컨트롤러의 메서드의 특정 파라미터를 지원하는지 판단한다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        //해당 파라미터에 LoginUser어노테이션이 있는지 확인하는 변수
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        //컨트롤러 메서드의 특정 파라미터가 세션유저 클래스와 타입이 맞는지 확인 - 세션객체를 넣어주기 위한 어노테이션이므로 세션클래스와 비교
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        //두 변수 모두 True라면, True를 리턴해서 세션객체를 해당 파라미터에 바인딩하는 아래의 resolveArgument함수를 실행함.
        return isLoginUserAnnotation && isUserClass;
    }

    //실제로 값을 넘겨주는 로직을 실행하는 메서드이다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }
}
