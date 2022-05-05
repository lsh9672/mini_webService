package miniProject.miniwebProject.service.post;

import miniProject.miniwebProject.domain.post.Posts;
import miniProject.miniwebProject.domain.post.PostsRepository;
import miniProject.miniwebProject.web.dto.PostsListResponseDto;
import miniProject.miniwebProject.web.dto.PostsResponseDto;
import miniProject.miniwebProject.web.dto.PostsSaveRequestDto;
import miniProject.miniwebProject.web.dto.PostsUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostsServiceTest {

    @Autowired PostsService postsService;

    @Autowired PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @DisplayName("서비스계층 저장 테스트")
    @Test
    public void serviceSaveTest() throws Exception{
        //give
        String title = "title111";
        String content = "content111";
        String author = "author111";
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto(title, content, author);

        //when
        Long saveId = postsService.save(requestDto);

        Posts posts = postsRepository.findById(saveId)
                .orElseThrow();

        //then
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo(author);
    }

    @DisplayName("서비스계층 수정 테스트")
    @Test
    public void serviceUpdateTest() throws Exception{
        //give
        //테스트를 위해 데이터를 넣을떈 레파지토리를 이용함 - 이 테스트는 서비스 계층에서 저장을 테스트 하는 것이 아닌 수정을 테스트 하는 것이므로
        String title = "title111";
        String content = "content111";
        String author = "author111";
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto(title, content, author);


        Posts save = postsRepository.save(requestDto.toEntity());

        String updateTitle = "jpa";
        String updateContent = "spring data";

        //when
        Long updateId = postsService.update(save.getId(), new PostsUpdateRequestDto(updateTitle, updateContent));

        Posts posts = postsRepository.findById(updateId)
                .orElseThrow();
        //then
        assertThat(posts.getTitle()).isEqualTo(updateTitle);
        assertThat(posts.getContent()).isEqualTo(updateContent);

        //없는 데이터 수정하려고 했을때 예외터지는지 확인
        assertThrows(IllegalArgumentException.class, () -> {
            postsService.update(2L,new PostsUpdateRequestDto(updateTitle, updateContent));
        });
    }

    @DisplayName("서비스 계층 단일 조회 테스트")
    @Test
    public void serviceFindOneTest() throws Exception{
        //give
        String title = "title111";
        String content = "content111";
        String author = "author111";
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto(title, content, author);
        Posts save = postsRepository.save(requestDto.toEntity());

        //when
        PostsResponseDto byId = postsService.findById(save.getId());

        //then
        assertThat(byId.getTitle()).isEqualTo(title);
        assertThat(byId.getContent()).isEqualTo(content);
        assertThat(byId.getAuthor()).isEqualTo(author);

        //예외상황
        assertThrows(IllegalArgumentException.class, ()->{
            postsService.findById(2L);
        });
    }

    @DisplayName("서비스 계층 전체 조회 테스트")
    @Test
    public void serviceFindAllTest() throws Exception{
        //give
        String title = "title111";
        String content = "content111";
        String author = "author111";
        PostsSaveRequestDto requestDto = new PostsSaveRequestDto(title, content, author);
        Posts save = postsRepository.save(requestDto.toEntity());

        //when

        List<PostsListResponseDto> allDesc = postsService.findAllDesc();
        PostsListResponseDto postsListResponseDto = allDesc.get(0);

        //then
        assertThat(allDesc.size()).isEqualTo(1);
        assertThat(postsListResponseDto.getTitle()).isEqualTo(title);
        assertThat(postsListResponseDto.getAuthor()).isEqualTo(author);

    }

    @DisplayName("서비스 계층 삭제 테스트")
    @Test
    public void serviceDeleteTest() throws Exception{
        //give
        String title = "title111";
        String content = "content111";
        String author = "author111";
        PostsSaveRequestDto requestDto1 = new PostsSaveRequestDto(title, content, author);
        Posts save1 = postsRepository.save(requestDto1.toEntity());

        String title2 = "title222";
        String content2 = "content222";
        String author2 = "author222";
        PostsSaveRequestDto requestDto2 = new PostsSaveRequestDto(title2, content2, author2);
        Posts save2 = postsRepository.save(requestDto2.toEntity());

        //when
        postsService.delete(save1.getId());

        List<Posts> allDesc = postsRepository.findAllDesc();
        //then
        assertThat(allDesc.size()).isEqualTo(1);
        assertThat(allDesc.get(0).getId()).isEqualTo(save2.getId());

        assertThrows(IllegalArgumentException.class , ()->{
            postsService.delete(save1.getId());
        });
    }


}