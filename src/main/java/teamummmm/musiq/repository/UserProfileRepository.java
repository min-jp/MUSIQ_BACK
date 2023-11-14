package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.UserProfileEntity;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    Boolean existsByLoginId(String loginId);  // login id가 이미 존재하는지 확인

    UserProfileEntity findByLoginId(String loginId);  // login id로 UserProfileEntity 찾기
}
