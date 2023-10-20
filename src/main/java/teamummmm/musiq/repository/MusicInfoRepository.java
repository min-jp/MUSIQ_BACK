package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.MusicInfoEntity;

@Repository
public interface MusicInfoRepository extends JpaRepository<MusicInfoEntity, String> {
}
