package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.AnswerPageQsEntity;

@Repository
public interface AnswerPageQsRepository extends JpaRepository<AnswerPageQsEntity, Long> {
}
