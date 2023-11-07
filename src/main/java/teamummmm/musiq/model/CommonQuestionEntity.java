package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Data // getter, setter
@Entity
@Table(name = "CommonQuestion")
public class CommonQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commonQuestionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    private CategoryVal category;  // 질문 카테고리

    private float valence;  // 음악 valence

    private float tempo;  // 음악 tempo

    private float energy;  // 음악 energy

    private float danceability;  // 음악 danceability

    @OneToOne
    private CommonQuestionEntity followupQuestion;  // 후속질문 아이디 - fk(CommonQuestion)
}
