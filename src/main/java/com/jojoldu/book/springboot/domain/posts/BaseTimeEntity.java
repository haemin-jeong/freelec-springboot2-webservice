package com.jojoldu.book.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//JPA Auditing 기능을 사용하여 BaseTimeEntity를 상속받은 클래스는 자동으로 createdDate, modifiedDate 컬럼이 자동으로 생성되고 관리된다.
@Getter
@MappedSuperclass //해당 클래스를 상속한 엔티티들은 해당 클래스의 필드들을 컬럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class) //해당 클래스에 Auditing 기능을 포함시킨다.
public class BaseTimeEntity {

    @CreatedDate //엔티티가 생성되어 저장될 때의 시간을 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 엔티티의 값을 변경할 떄의 시간을 자동 저장
    private LocalDateTime modifiedDate;

}
