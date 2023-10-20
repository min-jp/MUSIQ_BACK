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
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // 오브젝트 아이디

    private String loginId;  // 로그인 유저 아이디

    private String userName; // 사용자 이름

    /*@OneToMany(mappedBy = "user")
    private List<SharePageQsEntity> sharePageQsList = new ArrayList<>();  // SharePageQs 양방향

    @OneToMany(mappedBy = "user")
    private List<AnswerPageQsEntity> answerPageQsList = new ArrayList<>();  // nswerPageQs 양방향*/
}
