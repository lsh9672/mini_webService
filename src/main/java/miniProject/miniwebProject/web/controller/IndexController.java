package miniProject.miniwebProject.web.controller;

import lombok.RequiredArgsConstructor;
import miniProject.miniwebProject.config.auth.LoginUser;
import miniProject.miniwebProject.config.auth.dto.SessionUser;
import miniProject.miniwebProject.service.post.PostsService;
import miniProject.miniwebProject.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * 화면찍는 컨트롤러
 */

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

        model.addAttribute("userName",user);

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }


}
