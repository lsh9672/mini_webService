package miniProject.miniwebProject.web.controller;


import lombok.extern.slf4j.Slf4j;
import miniProject.miniwebProject.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){

        log.info("hello check");

        return "Hello";
    }

    @GetMapping("/hello/v2")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){
        log.info("Hello_Dto check");
        return new HelloResponseDto(name,amount);

    }
}
