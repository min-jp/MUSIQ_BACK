package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.OtherUserEntity;

@Repository
public interface OtherUserRepository extends JpaRepository<OtherUserEntity, Long> {
}
