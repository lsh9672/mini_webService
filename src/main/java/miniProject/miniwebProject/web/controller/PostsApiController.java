package miniProject.miniwebProject.web.controller;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.service.post.PostsService;
import miniProject.miniwebProject.web.dto.PostsResponseDto;
import miniProject.miniwebProject.web.dto.PostsSaveRequestDto;
import miniProject.miniwebProject.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

/**
 * 게시물관련 API 요청 처리를 하는 컨트롤러
 */


@RequiredArgsConstructor
@RestController //API 요청은 request가 뷰를 호출하는 것이 아닌, http body에 데이터를 넣어서 처리해야 됨.
public class PostsApiController {
    //컨트롤러를 다루는 계층은 서비스 계층을 의존함(반대는 성립 x)
    //@RequiredArgsConstructor를 통해 아래의 변수로 생성자를 만들었기 때문에 @Autowired가 생략되어, 생성자 주입이 발생함.

    private final PostsService postsService;

    /**
     * 등록 컨트롤러
     */
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){

        //서비스 계층의 save함수를 호출하므로써 데이터를 저장하고, id값을 반환받음
        return postsService.save(requestDto);
    }

    /**
     *
     * 수정 컨트롤러
     */

    @PutMapping("/api/v1/posts/{id}") // 수정은 put또는 Patch를 씀(게시물의 경우, 데이터의 일부만 수정하기 어렵기 떄문에 아예 새로운것으로 갈아끼우도록 put을 이용함)
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    /**
     * 조회 컨트롤러
     */
    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }


}
