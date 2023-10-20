package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.AnswerPageAnsEntity;
import teamummmm.musiq.model.AnswerPageQsEntity;
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

    private AnswerPageQsEntity answerPageQs;  // 질문 아이디 - fk (AnswerPageQs)

    public AnswerPageAnsDTO(final AnswerPageAnsEntity entity) {
        this.answerId = entity.getAnswerId();
        this.musicInfo = entity.getMusicInfo();
        this.answerDate = entity.getAnswerDate();
        this.caption = entity.getCaption();
        this.answerPageQs = entity.getAnswerPageQs();
    }

    public static AnswerPageAnsEntity toEntity(final AnswerPageAnsDTO dto) {
        return AnswerPageAnsEntity.builder()
                .answerId(dto.getAnswerId())
                .musicInfo(dto.getMusicInfo())
                .answerDate(dto.getAnswerDate())
                .caption(dto.getCaption())
                .answerPageQs(dto.getAnswerPageQs())
                .build();
    }
}
