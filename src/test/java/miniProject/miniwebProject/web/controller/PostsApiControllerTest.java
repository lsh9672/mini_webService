package miniProject.miniwebProject.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miniProject.miniwebProject.domain.post.Posts;
import miniProject.miniwebProject.domain.post.PostsRepository;
import miniProject.miniwebProject.web.dto.PostsResponseDto;
import miniProject.miniwebProject.web.dto.PostsSaveRequestDto;
import miniProject.miniwebProject.web.dto.PostsUpdateRequestDto;
import org.apache.juli.logging.Log;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * "HelloController"와 달리 컨트롤러인데도 불구하고 @WebMvcTest를 사용하지 않는 이유는,
 * @WebMvcTest 이 어노테이션을 사용하면 JPA 기능이 동작하지 않기 때문이다.
 * @Controller @ControllerAdvice와 같이 외부연동과 관련된 부분만 활성화 된다.
 * 따라서 JPA까지 테스트하려면 @SpringBootTest의 TestRestTemplate을 사용해야 된다.
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Autowired private PostsRepository postsRepository;

    //스프링부트테스트에서 MockMVC사용하기
    @Autowired private WebApplicationContext context;

    private MockMvc mvc;
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @DisplayName("게시글 등록")
    @Test
    @WithMockUser(roles="USER") //인증된 모의 사용자를 만듦 - MockMVC에서만 작동함
    public void postsSaveTest() throws Exception{
        //give
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                                .andExpect(status().isOk());


        //then
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("게시글 수정")
    @Test
    @WithMockUser(roles="USER")
    public void postsUpdateTest() throws Exception{
        //give

        //수정할 데이터가 먼저 저장되어야 됨
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //수정할 값들
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

         //HttpEntity를 이용해서 바디에 데이터를 넣음
//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        //greaterThan은 ~~보다 크다라는 뜻으로, 하나 생성이 되었으면 id값이 최소 0보다 클것이므로 아래와 같이 테스트한다.
//        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    //책에는 없길래 직접 만들어봄
    @DisplayName("게시글 조회")
    @Test
    @WithMockUser(roles="USER")
    public void postsFindTest() throws Exception{
        //give
        String title = "JPA";
        String content = "DB";
        String author = "Lee";

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Long findId = savedPosts.getId();
        //when
        String url = "http://localhost:" + port + "/api/v1/posts/" + findId;
//        ResponseEntity<PostsResponseDto> forEntity = restTemplate.getForEntity(url, PostsResponseDto.class);

        mvc.perform(get(url))
                .andExpect(status().isOk());

        //then
//        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(forEntity.getBody().getId()).isEqualTo(findId);
//        Assertions.assertThat(forEntity.getBody().getTitle()).isEqualTo(title);
//        Assertions.assertThat(forEntity.getBody().getContent()).isEqualTo(content);
//        Assertions.assertThat(forEntity.getBody().getAuthor()).isEqualTo(author);

    }

    /**
     * 게시글 삭제 컨트롤러 테스트
     * 삭제한 것 조회는 서비스 테스트에서 함
     */
    @DisplayName("게시글 삭제 테스트")
    @Test
    @WithMockUser(roles="USER")
    public void postDeleteTest() throws Exception{
        //give
        String title= "title";
        String content = "content";
        String author = "author";

        Posts saveId = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Long id = saveId.getId();

        //when
        String url = "http://localhost:" + port + "/api/v1/posts/" + id;
        //반환값이 없다면 delete를 사용, 컨트롤러의 반환값을 가지기 위해서는 아래와 같이 exchange를 사용한다.
        //exchange를 사용하면 HTTPENtity를 사용하기 때문에 해더를 넣어주어야 한다.
//        HttpHeaders header = new HttpHeaders();
//        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(header);
//        ResponseEntity<Long> exchange = restTemplate.exchange(url, HttpMethod.DELETE, objectHttpEntity, Long.class);
        mvc.perform(delete(url))
                .andExpect(status().isOk());



        //then
//        Assertions.assertThat(id).isEqualTo(exchange.getBody());
        assertThrows(NoSuchElementException.class , ()->{
            postsRepository.findById(id).orElseThrow();
        });



    }

}