package teamummmm.musiq.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Getter
@DynamicInsert
@Entity
@Table(name = "UserProfile")
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // 오브젝트 아이디

    @Column(unique = true)
    private String loginId;  // 로그인 유저 아이디

    private String userName; // 사용자 이름

    @ColumnDefault("0")
    private Float valence;  // 음악 valence

    @ColumnDefault("0")
    private Float energy;  // 음악 energy

    @ColumnDefault("0")
    private Float danceability;  // 음악 danceability

    public void updateAudioFeatures(Float valence, Float energy, Float danceability) {
        this.valence = valence;
        this.energy = energy;
        this.danceability = danceability;
    }
}
