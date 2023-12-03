package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.*;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@Entity
@Table(name = "CommonQuestion")
public class CommonQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commonQuestionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    private CategoryVal category;  // 질문 카테고리

    private Float valence;  // 음악 valence

    private Float energy;  // 음악 energy

    private Float danceability;  // 음악 danceability

    @OneToOne
    private CommonQuestionEntity followupQuestion;  // 후속질문 아이디 - fk(CommonQuestion)

    public void updateFollowupQuestion(CommonQuestionEntity followupQuestion) {
        this.followupQuestion = followupQuestion;
    }
}
