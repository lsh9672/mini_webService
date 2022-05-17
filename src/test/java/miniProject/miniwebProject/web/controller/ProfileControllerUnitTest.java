package miniProject.miniwebProject.web.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.*;


class ProfileControllerUnitTest {

    @DisplayName("real proile 조회확인")
    @Test
    public void realProfileCheckTest() throws Exception{
        //give
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        Assertions.assertThat(profile).isEqualTo(expectedProfile);

    }

    @DisplayName("profile이 없으면 default 조회 확인")
    @Test
    public void notActiveProfileCheckTest() throws Exception{
        //give
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }
}