package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.AnswerPageQsEntity;
import teamummmm.musiq.model.UserEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerPageQsDTO {
    private Long questionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    private UserEntity user;  // 유저 아이디 - fk (User)

    public AnswerPageQsDTO(final AnswerPageQsEntity entity) {
        this.questionId = entity.getQuestionId();
        this.questionMsg = entity.getQuestionMsg();
        this.emoji = entity.getEmoji();
        this.user = entity.getUser();
    }

    public static AnswerPageQsEntity toEntity(final AnswerPageQsDTO dto) {
        return AnswerPageQsEntity.builder()
                .questionId(dto.getQuestionId())
                .questionMsg(dto.getQuestionMsg())
                .emoji(dto.getEmoji())
                .user(dto.getUser())
                .build();
    }
}
