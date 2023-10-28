package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.AnswerEntity;

@Repository
public interface AnswerPageAnsRepository extends JpaRepository<AnswerEntity, Long> {
}
