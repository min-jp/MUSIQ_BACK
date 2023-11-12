package teamummmm.musiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamummmm.musiq.model.UserQuestionEntity;

import java.util.List;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestionEntity, Long> {
    List<UserQuestionEntity> findByUser_UserIdAndAnswerPageAnsListIsNotEmpty(Long userId);  // 답변한 질문 리스트

    List<UserQuestionEntity> findByUser_UserIdAndAnswerPageAnsListIsEmpty(Long userId);  // 답변하지 않은 질문 리스트
}
