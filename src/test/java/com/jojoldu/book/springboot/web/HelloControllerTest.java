package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {
    //웹 API를 테스트할 때 사용, 스프링 MVC 테스트의 시작점
    //HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws  Exception {
        String hello = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello")) // /hello 주소로 HTTP GTE 요청을 한다.
                // mvc.perform의 결과를 검증.
                .andExpect(MockMvcResultMatchers.status().isOk())  //응답 코드가 200 OK인지 검증
                .andExpect(MockMvcResultMatchers.content().string(hello)); //mvc.perform의 결과를 검증. 응답 본문의 내용이 hello인지 검증
    }
}