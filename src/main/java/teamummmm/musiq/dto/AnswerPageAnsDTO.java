package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.model.MusicInfoEntity;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerPageAnsDTO {
    private Long answerId;  // 오브젝트 아이디

    private MusicInfoEntity musicInfo;  // 음악 아이디 - fk (MusicInfo)

    private LocalDate answerDate;  // 대답 날짜

    private String caption;  // 캡션

    private UserQuestionEntity answerPageQs;  // 질문 아이디 - fk (AnswerPageQs)

    public AnswerPageAnsDTO(final AnswerEntity entity) {
        this.answerId = entity.getAnswerId();
        this.musicInfo = entity.getMusicInfo();
        this.answerDate = entity.getAnswerDate();
        this.caption = entity.getCaption();
        this.answerPageQs = entity.getAnswerPageQs();
    }

    public static AnswerEntity toEntity(final AnswerPageAnsDTO dto) {
        return AnswerEntity.builder()
                .answerId(dto.getAnswerId())
                .musicInfo(dto.getMusicInfo())
                .answerDate(dto.getAnswerDate())
                .caption(dto.getCaption())
                .answerPageQs(dto.getAnswerPageQs())
                .build();
    }
}
