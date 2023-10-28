package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.categoryVal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonQuestionDTO {
    private Long commonQuestionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    private categoryVal category;  // 질문 카테고리

    private float valence;  // 음악 valence

    private float tempo;  // 음악 tempo

    private float energy;  // 음악 energy

    private float danceability;  // 음악 danceability

    private CommonQuestionEntity followupQuestion;  // 후속질문 아이디 - fk(CommonQuestion)

    public CommonQuestionDTO(final CommonQuestionEntity entity) {
        this.commonQuestionId = entity.getCommonQuestionId();
        this.questionMsg = entity.getQuestionMsg();
        this.emoji = entity.getEmoji();
        this.category = entity.getCategory();
        this.valence = entity.getValence();
        this.tempo = entity.getTempo();
        this.energy = entity.getEnergy();
        this.danceability = entity.getDanceability();
        this.followupQuestion = entity.getFollowupQuestion();
    }

    public static CommonQuestionEntity toEntity(final CommonQuestionDTO dto) {
        return CommonQuestionEntity.builder()
                .commonQuestionId(dto.getCommonQuestionId())
                .questionMsg(dto.getQuestionMsg())
                .emoji(dto.getEmoji())
                .category(dto.getCategory())
                .valence(dto.getValence())
                .tempo(dto.getTempo())
                .energy(dto.getEnergy())
                .danceability(dto.getDanceability())
                .followupQuestion(dto.getFollowupQuestion())
                .build();
    }
}
