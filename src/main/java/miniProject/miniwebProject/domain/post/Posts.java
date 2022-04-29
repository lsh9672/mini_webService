package miniProject.miniwebProject.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본생성자가 있어야 JPA가 이것을 이용해서 이것저것 동작하게 됨
@Entity
public class Posts {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title,String content,String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }


}
