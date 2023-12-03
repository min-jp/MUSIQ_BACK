package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.UserQuestionEntity;

import java.util.List;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestionEntity, Long> {
    List<UserQuestionEntity> findByUser_UserIdAndAnswerPageAnsListIsNotEmpty(Long userId);  // 답변한 질문 리스트

    @Query("SELECT u " +
            "FROM UserQuestionEntity u " +
            "WHERE u.user.userId = ?1 " +
            "AND u.answerPageAnsList IS EMPTY " +
            "AND u.commonQuestion.category != 0")  // 디폴트 질문 제외
    List<UserQuestionEntity> mainQuestionList(Long userId);  // 디폴트 질문 제외한 메인 질문

    @Query("SELECT u " +
            "FROM UserQuestionEntity u " +
            "WHERE u.user.userId = ?1 " +
            "AND u.answerPageAnsList IS EMPTY " +
            "AND u.commonQuestion.category = 0")
    List<UserQuestionEntity> defaultQuestionList(Long userId);  // 디폴트 질문

    UserQuestionEntity findByCommonQuestionAndUser_UserId(CommonQuestionEntity entity, Long userId);  // 유저의 후속 질문

    List<UserQuestionEntity> findByCommonQuestion_CommonQuestionId(Long commonQuestionId);  // commonQuestionId인 유저별 질문 리스트
}
