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
@Table(name = "OtherUser")
public class OtherUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // 오브젝트 아이디

    private String nickname;  // 타 사용자 별명

    @OneToMany(mappedBy = "otherUser")
    private List<OtherUserRecordEntity> otherUserRecordList = new ArrayList<>();  // OtherUserRecord 양방향
}
