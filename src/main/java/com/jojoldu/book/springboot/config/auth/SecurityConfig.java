package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 설정들 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
                .and()
                    .authorizeRequests() //URL 별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/image/**", "/js/**", "/h2-console/**") //권한 관리 대상을 지정하는 옵션, URL과 HTTP 메서드별로 관리가 가능하다.
                        .permitAll() //지정된 URL에 전체 열람 권한을 준다.
                    .antMatchers("/api/v1/**")
                        .hasRole(Role.USER.name()) //지정된 URL에 USER 권한을 가진 사람만 가능하다.
                    .anyRequest() //설정된 값 외에 나머지 URL 들을 나타낸다
                        .authenticated() //나머지 URL에 인증된 사용자들에게만 허용
                .and()
                    .logout()//로그아웃 기능에 대한 여러 설정의 진입점
                        .logoutSuccessUrl("/") //로그아웃 성공기 / 주소로 이동
                .and()
                    .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                            .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체를 등록. 사용자 정보를 가져온 상태에서 추가로 진행할 기능을 명시할 수 있다.
    }
}