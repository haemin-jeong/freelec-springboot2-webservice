package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//Entity와 Entity Repository는 같은 위치에 있어야한다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
