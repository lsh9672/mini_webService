package miniProject.miniwebProject.config.auth;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.config.auth.dto.OAuthAttributes;
import miniProject.miniwebProject.domain.user.User;
import miniProject.miniwebProject.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 소셜 로그인 이후에 가져온 사용자의 정보(email, name, picture등)를 기반으로 가입 및 정보수정, 세션 저장  등의 기능들을 지원
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();//현재 로그인 진행중인 서비스를 구분하는 코드, 구글만 사용하는 불필요한 값이지만, 나중에 네이버 로그인 연동시에 네이버 로그인인지 구글 로그인인지 구분하기 위해 사용함.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();// OAuth2 로그인 진행시 키가 되는 필드값을 말함, Primary Key와 같은 의미,구글은 기본적으로 코드를 지원함(코드명:sub), 네이버나 카카오는 기본지원안함. - 나중에 네이버와 구글 동시지원시에 사용될 예정

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,oAuth2User.getAttributes());//OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user",new SeesionUser(user)); // 세션에 사용자 정보를 저장하기 위한 Dto클래스

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    //구글 사용자 정보가 업데이트 되었을떄를 대비한 기능도 구현
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
