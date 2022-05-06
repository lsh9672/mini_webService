package miniProject.miniwebProject.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 소셜 로그인 기능에서 반환되는 이메일로 중복 사용자 여부를 체크하기 위함.
     */
    Optional<User> findByEmail(String email);
}
