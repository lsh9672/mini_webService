package miniProject.miniwebProject.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 아래에서 사용하는 assertThat은 import org.assertj.core.api.Assertions; 이것이 제공하는 것이다.
 * junit의 Assertions에는 assertThat이 없다
 */

class HelloResponseDtoTest {

    @DisplayName("롬복 기능 테스트")
    @Test
    public void lombokTest() throws Exception{
        //give
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name,amount);

        //then
        Assertions.assertThat(dto.getName()).isEqualTo(name);
        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
    }

}