package teamummmm.musiq.controller;

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
@Table(name = "OtherUserRecord")
public class OtherUserRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;  // 오브젝트 아이디

    @ManyToOne
    private OtherUserEntity otherUser;  // 사용자 아이디 - fk (OtherUser)

    @ManyToOne
    private SharePageAnsEntity sharePageAns;  // 음악 아이디 - fk (SharePageAns)
}
