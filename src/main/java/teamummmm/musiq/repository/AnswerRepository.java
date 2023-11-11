package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.ColorVal;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    @Query("SELECT a.musicInfo.musicColor " +
            "FROM AnswerEntity a " +
            "WHERE a.userQuestion.userQuestionId = ?1 " +
            "GROUP BY a.musicInfo.musicColor " +
            "ORDER BY COUNT(a.musicInfo.musicColor) DESC " +
            "LIMIT 1")
    ColorVal findBestColor(Long userQuestionId);  // 질문의 메인 컬러 찾기

    @Query("SELECT COUNT(e) = 1 " +
            "FROM AnswerEntity e " +
            "WHERE e.userQuestion.userQuestionId = ?1 " +
            "AND e.answerDate = ?2")
    boolean isFirstAnswer(Long userQuestionId, LocalDate nowDate);  // 오늘 남긴 첫 번째 질문인지 확인

    @Query("SELECT a.musicInfo.musicColor, COUNT(a.musicInfo.musicColor) AS colorCount " +
            "FROM AnswerEntity a " +
            "WHERE a.userQuestion.userQuestionId = ?1 " +
            "GROUP BY a.musicInfo.musicColor " +
            "ORDER BY colorCount DESC")
    List<Object[]> countMusicColors(Long userQuestionId);  // 질문별 색깔 비율

    List<AnswerEntity> findByUserQuestion_UserQuestionId(Long userQuestionId);  // UserQuestionId로 AnswerEntity 찾기
}
