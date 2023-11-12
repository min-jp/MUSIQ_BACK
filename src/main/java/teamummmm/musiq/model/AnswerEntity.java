package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@Entity
@Table(name = "Answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;  // 오브젝트 아이디

    @ManyToOne
    private MusicInfoEntity musicInfo;  // 음악 아이디 - fk (MusicInfo)

    private LocalDate answerDate;  // 대답 날짜

    private String caption;  // 캡션

    @ManyToOne
    private UserQuestionEntity userQuestion;  // 질문 아이디 - fk (UserQuestion)

    public void updateCaption(String caption) {
        this.caption = caption;
    }
}
