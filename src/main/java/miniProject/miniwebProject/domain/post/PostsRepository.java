package miniProject.miniwebProject.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    /**
     * spring data jpa가 제공하지 않는 기능은 만들어 쓸수 있음
     * 아래의 코드는 제공기능으로 해결가능하지만 이렇게 쿼리를 직접 짜서 쓸수도 있음
     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
