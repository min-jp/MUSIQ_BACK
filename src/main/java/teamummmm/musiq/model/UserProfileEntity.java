package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@Entity
@Table(name = "UserProfile")
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // 오브젝트 아이디

    private String loginId;  // 로그인 유저 아이디

    private String userName; // 사용자 이름

    @ColumnDefault("0")
    private int consecutiveDates;  // 연속으로 대답한 날
}
