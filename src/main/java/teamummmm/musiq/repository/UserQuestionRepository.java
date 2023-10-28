package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.UserQuestionEntity;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestionEntity, Long> {
}
