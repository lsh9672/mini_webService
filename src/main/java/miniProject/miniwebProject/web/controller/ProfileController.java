package miniProject.miniwebProject.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 무중단 배포시에 어떤 서버로 요청이 가야되는지 판단하는 기준
 */

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile(){

        //현재 실행중인 ActiveProfile 즉, real,oauth,real-db와 같은 설정파일들을 가져옴
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        List<String> realProfiles = Arrays.asList("real", "real1", "real2");

        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny().orElse(defaultProfile);
    }


}
