package miniProject.miniwebProject.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 모든 엔티티들의 상위 클래스가 되어서 아래의 두 항목에 대해 자동으로 관리
 * 아래의 두 "생성시간", "변경시간" 은 공통사항이므로 이와같이 처리
 * (프록시나 AOP를 이용해서 이와같은 공통 관심사를 처리하는 방법으로 해도될것같음)
 * 추가로 이 auditing기능은 spring data jpa가 제공해주는 기능이다.
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
