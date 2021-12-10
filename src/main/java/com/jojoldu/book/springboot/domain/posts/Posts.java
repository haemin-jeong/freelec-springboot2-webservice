package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//엔티티 클래스에서는 절대 Setter 메서드를 만들지 말고, 어떤 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 이름의 메서드를 만들자.
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    //PK는 Long 타입의 AUTO_INCREMENT를 추천
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //스프링부트 2.0에서는 IDENTITY 전략을 사용해야 AUTO_INCREMENT가 된다.
    private Long id;

    //문자열의 경우 VARCHAR(255)가 디폴트 값인데 길이를 500으로 변경
    @Column(length = 500, nullable = false)
    private String title;

    ////문자열의 경우 VARCHAR(255)가 디폴트 값인데 TEXT 타입으로 변경
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
