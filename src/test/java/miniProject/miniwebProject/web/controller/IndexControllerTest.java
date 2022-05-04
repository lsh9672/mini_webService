package miniProject.miniwebProject.web.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("메인페이지 로딩 확인")
    @Test
    public void mainPageLoadingTest() throws Exception{
        //give


        //when
        String body = this.restTemplate.getForObject("/",String.class);

        //then
        Assertions.assertThat(body).contains("스프링 부트 웹서비스");
    }


}