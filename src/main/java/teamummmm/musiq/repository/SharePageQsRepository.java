package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.SharePageQsEntity;

@Repository
public interface SharePageQsRepository extends JpaRepository<SharePageQsEntity, Long> {
}
