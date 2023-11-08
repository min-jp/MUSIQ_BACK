package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@Entity
@Table(name = "UserQuestion")
public class UserQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQuestionId;  // 오브젝트 아이디

    @ColumnDefault("0")
    private int callCount;  // 질문 노출 횟수

    @ColumnDefault("0")
    private int answerCount;  // 답변 횟수 (답변 개수가 아니라 특정 질문에 대해서 몇번 답변했는지)

    @ManyToOne
    private UserProfileEntity user;  // 유저 아이디 - fk (User)

    @ManyToOne
    private CommonQuestionEntity commonQuestion;  // 공통 질문 아이디 - fk (CommonQuestion)

    @OneToMany(mappedBy = "userQuestion")
    private List<AnswerEntity> answerPageAnsList = new ArrayList<>();  // Answer 양방향
}
