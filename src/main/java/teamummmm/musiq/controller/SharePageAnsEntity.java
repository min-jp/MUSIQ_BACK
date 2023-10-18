package teamummmm.musiq.controller;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder  // 빌더로 오브젝트 생성
@NoArgsConstructor  // 매개변수 없는 생성자
@AllArgsConstructor  // 모든 매개변수 생성자
@Data // getter, setter
@Entity
@Table(name = "SharePageAns")
public class SharePageAnsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;  // 오브젝트 아이디

    @ManyToOne
    private MusicInfoEntity musicInfo;  // 음악 아이디 - fk (MusicInfo)

    private LocalDate answerDate;  // 대답 날짜

    @ManyToOne
    private SharePageQsEntity sharePageQs;  // 질문 아이디 - fk (SharePageQs)

    @OneToMany(mappedBy = "sharePageAns")
    private List<OtherUserRecordEntity> otherUserRecordList = new ArrayList<>();  // OtherUserRecord 양방향
}
