package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.SharePageAnsEntity;

@Repository
public interface SharePageAnsRepository extends JpaRepository<SharePageAnsEntity, Long> {
}
