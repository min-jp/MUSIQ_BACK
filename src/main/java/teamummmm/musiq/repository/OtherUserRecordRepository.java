package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.OtherUserRecordEntity;

@Repository
public interface OtherUserRecordRepository extends JpaRepository<OtherUserRecordEntity, Long> {
}
