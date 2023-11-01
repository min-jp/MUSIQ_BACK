package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.UserQuestionEntity;

import java.util.List;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestionEntity, Long> {
    List<UserQuestionEntity> findByUser_UserId(Long userId);
}
