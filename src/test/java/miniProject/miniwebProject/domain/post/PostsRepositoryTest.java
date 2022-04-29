package miniProject.miniwebProject.domain.post;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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


}