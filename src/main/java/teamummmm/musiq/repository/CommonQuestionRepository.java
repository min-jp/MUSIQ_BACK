package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.CommonQuestionEntity;

@Repository
public interface CommonQuestionRepository extends JpaRepository<CommonQuestionEntity, Long> {
}
