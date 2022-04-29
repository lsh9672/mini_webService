package miniProject.miniwebProject.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 데이터를 사용자에게 보여주거나 사용자로부터 받을떄는, 엔티티 그대로 json객체로 변환해서 보여주는 식이 아닌,
 * 주고받는 데이터만 정확하게 담을수 있는 알맞은 DTO를 사용해야된다.
 * 엔티티를 그대로 사용하게 되면 문제가 발생할 수 있다.
 * 예를 들어 로그인으로 하려고 할떄나, 로그인 정보를 보여줄때, 회원테이블 엔티티에 데이터를 담아서 주고 받으면,
 * 사용자가 보면 안되는 패스워드 같은 것들도 같이 담겨서 넘어갈 수 있다.
 * 또한 이런것은 작은 프로젝트나 시도라도 가능한것이지, 요구사항이 많은 거대한 프로젝트라면, 회원가입시와 로그인정보 요청시의
 * 데이터가 너무 크게 달라서 엔티티를 이용할 수 조차 없게 된다.
 *
 * 따라서 주고 받을떄 각각에 맞는 데이터 전송 객체(DTO-줄여서 이렇게 많이씀)를 만들어서 이것에 담아서 사용해야 된다.
 */

/**
 * 추가로 아래를 보면 setter는 사용하지 않고 파라미터가 있는 생성자를 사용했는데,
 * 이 이유는 setter를 열어두면, 누군가와 협업했을때, 실수로 또는 setter가 열려있으니, 사용해도 되는구나 생각해서 고의로, 값을 바꿔버릴수 있다.
 * 개발시에는 애초에 문제가 발생할수 없도록 원천 봉쇄하는 것이 좋다.
 * 아래의 @RequiredArgsConstructor를 사용하면 final키워드가 붙은 필드변수를 파라미터로 갖는 생성자를 만들게 된다.
 * 그렇게 되면, 생성자를 통해서만 값을 변경할 수 있어서, 임의로 필드변수의 값이 바뀌는 사태를 방지할 수 있다.
 */

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;
}
