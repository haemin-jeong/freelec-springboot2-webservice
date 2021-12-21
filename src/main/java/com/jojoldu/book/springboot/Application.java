package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing 가 삭제됨 -> @EnableJpaAuditing 을 사용하려면 최소 1개의 @Entity 클래스가 있어야한다.
//그런데 테스트에서 @WebMvcTest 를 사용하면 @Entity 를 스캔할 수 없기 때문에 'java.lang.IllegalArgumentException: JPA metamodel must not be empty!' 에러가 발생한다.
//그래서 @EnableJpaAuditing 을 JpaConfig 클래스로 별도 분리하였다.
@SpringBootApplication //스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성 모두 자동으로 설정된다.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); //내장 WAS를 실행
    }
}
