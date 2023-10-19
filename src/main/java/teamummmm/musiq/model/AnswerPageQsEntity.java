package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Data // getter, setter
@Entity
@Table(name = "AnswerPageQs")
public class AnswerPageQsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    @ManyToOne
    private UserEntity user;  // 유저 아이디 - fk (User)

    @OneToMany(mappedBy = "answerPageQs")
    private List<AnswerPageAnsEntity> answerPageAnsList = new ArrayList<>();  // SharePageAns 양방향
}
