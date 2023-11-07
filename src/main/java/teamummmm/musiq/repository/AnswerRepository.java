package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.ColorVal;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    @Query("SELECT a.musicInfo.musicColor " +
            "FROM AnswerEntity a " +
            "WHERE a.userQuestion.userQuestionId = ?1 " +
            "GROUP BY a.musicInfo.musicColor " +
            "ORDER BY COUNT(a.musicInfo.musicColor) DESC " +
            "LIMIT 1")
    ColorVal findBestColor(Long questionId);  // 질문의 메인 컬러 찾기
}
