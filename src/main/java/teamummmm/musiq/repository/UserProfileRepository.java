package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.UserProfileEntity;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    boolean existsByLoginId(String loginId);

    UserProfileEntity findByLoginId(String loginId);
}
