package teamummmm.musiq.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@Entity
@Table(name = "MusicInfo")
public class MusicInfoEntity {
    @Id
    private String musicId;  // 음악 아이디

    private ColorVal musicColor;  // 음악별 색깔
}
