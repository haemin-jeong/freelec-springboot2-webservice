package com.jojoldu.book.springboot.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest //별도의 설정없이 H2 데이터베이스를 자동으로 실행해 준다.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /*
    @After
    - 단위테스트가 끝날 떄마다 수행되는 메서드를 지정한다.
    - 보통은 배포전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용한다.
    - 여러 테스트가 동시에 수행되면서 디비에 데이터가 그대로 남아 다음 테스트 실행 시에 테스트가 실패할 수 있다.
     */
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }
}
