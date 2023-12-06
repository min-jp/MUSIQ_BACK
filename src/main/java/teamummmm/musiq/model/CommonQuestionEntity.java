package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@DynamicInsert  // 기본값 넣기
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

    @ColumnDefault("0")
    private Float avgValence;  // 사용자들의 평균 valence

    @ColumnDefault("0")
    private Float avgEnergy;  // 사용자들의 평균 energy

    @ColumnDefault("0")
    private Float avgDanceability;  // 사용자들의 평균 danceability

    public void updateAvgAudioFeatures(Float avgValence, Float avgEnergy, Float avgDanceability) {
        this.avgValence = avgValence;
        this.avgEnergy = avgEnergy;
        this.avgDanceability = avgDanceability;
    }

    public void updateFollowupQuestion(CommonQuestionEntity followupQuestion) {
        this.followupQuestion = followupQuestion;
    }
}
