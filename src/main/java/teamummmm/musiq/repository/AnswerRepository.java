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
    @Query("SELECT sub.color " +
            "FROM (SELECT a.musicInfo.musicColor AS color, " +
                    "MAX(a.timeStamp) AS max_timestamp, " +
                    "COUNT(a.musicInfo.musicColor) AS color_count " +
                    "FROM AnswerEntity a " +
                    "WHERE a.userQuestion.userQuestionId = ?1 " +
                    "GROUP BY a.musicInfo.musicColor) AS sub " +
            "ORDER BY sub.color_count DESC, sub.max_timestamp DESC " +
            "LIMIT 1")
    ColorVal findBestColor(Long userQuestionId);  // 질문의 메인 컬러 찾기

    @Query("SELECT COUNT(e) = 1 " +
            "FROM AnswerEntity e " +
            "WHERE e.userQuestion.userQuestionId = ?1 " +
            "AND e.answerDate = ?2")
    Boolean isFirstAnswerToday(Long userQuestionId, LocalDate nowDate);  // 오늘 남긴 첫 번째 질문인지 확인

    @Query("SELECT COUNT(e) = 1 " +
            "FROM AnswerEntity e " +
            "WHERE e.userQuestion.userQuestionId = ?1")
    Boolean isFirstAnswer(Long userQuestionId);  // 질문에 대한 첫 번째 질문인지 확인

    @Query("SELECT a.musicInfo.musicColor, COUNT(a.musicInfo.musicColor) AS colorCount " +
            "FROM AnswerEntity a " +
            "WHERE a.userQuestion.userQuestionId = ?1 " +
            "GROUP BY a.musicInfo.musicColor " +
            "ORDER BY colorCount DESC")
    List<Object[]> countMusicColors(Long userQuestionId);  // 질문별 색깔 비율

    List<AnswerEntity> findByUserQuestion_UserQuestionId(Long userQuestionId);  // UserQuestionId로 AnswerEntity 찾기

    List<AnswerEntity> findByUserQuestion_User_UserId(Long userId);  // UserId로 AnswerEntity 찾기

    List<AnswerEntity> findByAnswerDateAndUserQuestion_User_UserId(LocalDate answerDate, Long userId);  // AnswerDate와 UserId로 AnswerEntity 찾기

    Boolean existsByUserQuestion_User_UserId(Long userId);  // 대답을 한 적이 있는지 확인

    Long countByUserQuestion_User_UserId(Long userId);  // 그 유저의 모든 답변 개수

    Long countByUserQuestion_CommonQuestion_CommonQuestionId(Long commonQuestionId);  // 공통 질문의 모든 답변 개수
}
