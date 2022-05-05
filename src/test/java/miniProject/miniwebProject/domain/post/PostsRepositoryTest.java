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

    /**
     * @Query를 이용해 직접 작성한 data jpa의 메서드를 테스트
     * 테스트할 메서드 : findAllDesc - 게시판에 데이터를 내림차순 형태로 보여주기 위해서 만든 메서드
     */
    @DisplayName("내림차순 전체 조회 테스트")
    @Test
    public void descFindAllTest() throws Exception{
        //give
        //조회전에 값 두개 정도 넣기
        //1번값
        String title1 = "title1";
        String content1 = "content1";
        String author1 = "author1";

        //2번 값
        String title2 = "title2";
        String content2 = "content2";
        String author2 = "author2";

        postsRepository.save(Posts.builder()
                .title(title1)
                .content(content1)
                .author(author1)
                .build());

        postsRepository.save(Posts.builder()
                .title(title2)
                .content(content2)
                .author(author2)
                .build());

        //when
        //내림차순 조회
        List<Posts> allDesc = postsRepository.findAllDesc();

        Posts firstPost = allDesc.get(0);
        Posts secondPost = allDesc.get(1);

        //then
        Assertions.assertThat(allDesc.size()).isEqualTo(2);

        Assertions.assertThat(firstPost.getTitle()).isEqualTo(title2);
        Assertions.assertThat(firstPost.getContent()).isEqualTo(content2);
        Assertions.assertThat(firstPost.getAuthor()).isEqualTo(author2);

        Assertions.assertThat(secondPost.getTitle()).isEqualTo(title1);
        Assertions.assertThat(secondPost.getContent()).isEqualTo(content1);
        Assertions.assertThat(secondPost.getAuthor()).isEqualTo(author1);

    }

    /**
     * 레파지토리 삭제 테스트
     */
    @DisplayName("삭제 테스트")
    @Test
    public void deleteTest() throws Exception{
        //give
        String title= "title";
        String content = "content";
        String author = "author";

        Posts saveId = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        //when
        postsRepository.delete(saveId);

        List<Posts> all = postsRepository.findAll();

        //then

        Assertions.assertThat(all.size()).isEqualTo(0);
    }


}