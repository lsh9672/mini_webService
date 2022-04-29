package miniProject.miniwebProject.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 테스트의 경우, 직접 우클릭으로 만들기보단, 인텔리제이가 제공하는 단축키를 이용하면 편하다.
 * 단축키 : (테스트를 만들고자 하는 클래스 위치에서) ctrl + shift + T
 */

//JUNIT4까지는 테스트 클래스에 public을 붙여줘야 했지만, 5부터는 필요없게 되었다
// 책에서는 4를 기준으로 하지만, 현재 5를 많이 쓰고 있기 떄문에 5를 사용해서 진행하겠다
// Junit5에서는 Junit Platform, Junit Jupiter, Junit Vintage 3개



//Junit5 부터는 @RunWith 대신에 @ExtendWith를 사용한다.
//@ExtendWith(SpringExtension.class) // 아래의 어노테이션을 사용하면 이 @ExtendWith는 들어있기 떄문에 안써줘도 된다
@WebMvcTest(controllers = HelloController.class)  //이것은 서블릿 컨테이너를 모킹하기 위해서 필요하
class HelloControllerTest {

    //api 요청테스트를 위해서 서블릿 디스패처 객체를 흉내내는 목mvc를 자동주입 받는다.
    @Autowired MockMvc mvc;

    @DisplayName("hello 컨트롤러 테스트")
    @Test
    public void helloTest() throws Exception{
        //give
        //when
        //then
        String hello = "Hello";

        mvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @DisplayName("hello dto 컨트롤러 테스트")
    @Test
    public void helloDtoTest() throws Exception{
        //give
        String name = "hello";
        int amount = 1000;

        //when

        //then
        mvc.perform(get("/hello/v2")
                        .param("name",name)
                        .param("amount",String.valueOf(amount))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
    }

}