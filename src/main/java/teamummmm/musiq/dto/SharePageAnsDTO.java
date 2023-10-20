package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.MusicInfoEntity;
import teamummmm.musiq.model.SharePageAnsEntity;
import teamummmm.musiq.model.SharePageQsEntity;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SharePageAnsDTO {
    private Long answerId;  // 오브젝트 아이디

    private MusicInfoEntity musicInfo;  // 음악 아이디 - fk (MusicInfo)

    private LocalDate answerDate;  // 대답 날짜

    private SharePageQsEntity sharePageQs;  // 질문 아이디 - fk (SharePageQs)

    public SharePageAnsDTO(final SharePageAnsEntity entity) {
        this.answerId = entity.getAnswerId();
        this.musicInfo = entity.getMusicInfo();
        this.answerDate = entity.getAnswerDate();
        this.sharePageQs = entity.getSharePageQs();
    }

    public static SharePageAnsEntity toEntity(final SharePageAnsDTO dto) {
        return SharePageAnsEntity.builder()
                .answerId(dto.getAnswerId())
                .musicInfo(dto.getMusicInfo())
                .answerDate(dto.getAnswerDate())
                .sharePageQs(dto.getSharePageQs())
                .build();
    }
}
