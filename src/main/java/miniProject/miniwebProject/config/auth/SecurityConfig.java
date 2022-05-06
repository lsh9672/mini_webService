package miniProject.miniwebProject.config.auth;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 기능 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                        .headers().frameOptions().disable()//h2-console화면을 사용하기위해 옵션들 disable
                        .and()
                                .authorizeRequests()//URL별 권한 관리를 설정하는 옵션의 시작점,이것이 선언되어야만 antMatchers옵션 사용가능
                                        .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll() //권한 관리대상을 지정, URL,HTTP메소드별로 관리가 가능함. "/등 지정된 URL들은 permitALL로 전체 열람 권한을 줌"
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())//이 URL들은 USER권한을 가진 사람들만 가능하게 함.
                        .anyRequest().authenticated()//위에서 설정한 URL을 제외한 나머지 URL로, 이것들은 전부 인증된 사용자만 허용함.(인증된 사용자 : 로그인한 사용자)
                        .and()
                                .logout().logoutSuccessUrl("/")//로그아웃 기능에 대한 여러 설정의 진입점, 로그아웃 성공시에 "/"로 이동함
                                                .and()
                                                        .oauth2Login() //oauth2 로그인 기능에 대한 여러 설정의 진입점
                                                                .userInfoEndpoint()//oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                                                                        .userService(customOAuth2UserService);// 소셜로그인 성공시 후속조치를 진행할 UserService인터페이스의 구현체를 등록함.(리소스 서버- 소셜서비스 에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시함.)
    }
}
