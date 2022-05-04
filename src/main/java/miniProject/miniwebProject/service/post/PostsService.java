package miniProject.miniwebProject.service.post;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.domain.post.Posts;
import miniProject.miniwebProject.domain.post.PostsRepository;
import miniProject.miniwebProject.web.dto.PostsListResponseDto;
import miniProject.miniwebProject.web.dto.PostsResponseDto;
import miniProject.miniwebProject.web.dto.PostsSaveRequestDto;
import miniProject.miniwebProject.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 레포지토리 계층쪽을 의존하는 계층
 */

@RequiredArgsConstructor
@Service//서비스 계층임을 명시하면서도, 내부에 @Component 어노테이션이 있어서 스프링이 빈으로 등록함.
public class PostsService {

    private final PostsRepository postsRepository;

    //디비는 트랙잭션 단위로 동작하기 떄문에 하나에 트랜잭션에서 실행되도록 이 어노테이션을 붙임
    //이것이 붙은 메서드 안의 디비를 호출하는 로직들은 하나의 트랙잭션안에서 실행됨.
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity())
                .getId();
    }

    //수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    //단일조회
    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    //전체 조회
    public List<PostsListResponseDto> findAllDesc(){

        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());

    }

    //삭제
    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}

