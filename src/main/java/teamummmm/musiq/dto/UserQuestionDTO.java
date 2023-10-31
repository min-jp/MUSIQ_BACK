package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.model.UserProfileEntity;

/*
 * 사용 안함
 * */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserQuestionDTO {
    private Long userQuestionId;  // 오브젝트 아이디

    //private int callCount;  // 질문 노출 횟수

    //private int answerCount;  // 답변 횟수 (답변 개수가 아니라 특정 질문에 대해서 몇번 답변했는지)

    private UserProfileEntity user;  // 유저 아이디 - fk (User)

    private CommonQuestionEntity commonQuestion;  // 공통 질문 아이디 - fk (CommonQuestion)

    public UserQuestionDTO(final UserQuestionEntity entity) {
        this.userQuestionId = entity.getUserQuestionId();
        //this.callCount = entity.getCallCount();
        //this.answerCount = entity.getAnswerCount();
        this.user = entity.getUser();
        this.commonQuestion = entity.getCommonQuestion();
    }

    public static UserQuestionEntity toEntity(final UserQuestionDTO dto) {
        return UserQuestionEntity.builder()
                .userQuestionId(dto.getUserQuestionId())
                //.callCount(dto.getCallCount())
                //.answerCount(dto.getAnswerCount())
                .user(dto.getUser())
                .commonQuestion(dto.getCommonQuestion())
                .build();
    }
}
