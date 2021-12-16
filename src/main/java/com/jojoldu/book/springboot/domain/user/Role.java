package com.jojoldu.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    //스프링 시큐리티에서 권한 코드에는 ROLE_이 앞에 있어야만 한다.
    //그래서 코드별 키 값을 ROLE_GUEST, ROLE_USER로 지정
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
