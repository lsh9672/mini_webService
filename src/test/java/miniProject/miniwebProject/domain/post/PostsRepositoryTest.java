package miniProject.miniwebProject.domain.post;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsRepositoryTest {

    @Autowired PostsRepository postsRepository;

    /**
     * 각 테스트가 끝나고 실행되는 로직이다
     * Junit4에서는 @After, @Before 두 개를 사용했지만,
     * Junit5부터는 @AfterEach,@BeforeEach 로 설정을 해줘야 된다.
     */
    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @DisplayName("게시글 저장 불러오기")
    @Test
    public void postSaveTest() throws Exception{
        //give
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "lsh80165@gmail.com";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
        Assertions.assertThat(posts.getAuthor()).isEqualTo(author);
    }

    /**
     * Spring data jpa가 제공하는 auditing 기능 테스트
     */
    @DisplayName("base time entity 등록")
    @Test
    public void baseTimeEntityTest() throws Exception{
        //give
        LocalDateTime now = LocalDateTime.of(2022,5,3,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> createDate=" + posts.getCreatedDate()+", modifiedDate=" + posts.getModifiedDate());

        //isAfter는 해당 인자보다 미래일때, true를 리턴함.
        //저장되는 시점의 시간은 now로 지정한 시간보다 무조건 미래일수 밖에 없기 때문에 이와 같이 테스트코드를 짬
        //LocalDateTime.now() 로 테스트를 하려고 하면, 항상 성공하는 테스트일수가 없기 때문에 이렇게 안함(휴일등등 실행하는 날에 따라서 달라짐.)
        Assertions.assertThat(posts.getCreatedDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
    }


}