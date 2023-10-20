package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.OtherUserEntity;
import teamummmm.musiq.model.OtherUserRecordEntity;
import teamummmm.musiq.model.SharePageAnsEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OtherUserRecordDTO {
    private Long recordId;  // 오브젝트 아이디

    private OtherUserEntity otherUser;  // 사용자 아이디 - fk (OtherUser)

    private SharePageAnsEntity sharePageAns;  // 음악 아이디 - fk (SharePageAns)

    public OtherUserRecordDTO(final OtherUserRecordEntity entity) {
        this.recordId = entity.getRecordId();
        this.otherUser = entity.getOtherUser();
        this.sharePageAns = entity.getSharePageAns();
    }

    public static OtherUserRecordEntity toEntity(final OtherUserRecordDTO dto) {
        return OtherUserRecordEntity.builder()
                .recordId(dto.getRecordId())
                .otherUser(dto.getOtherUser())
                .sharePageAns(dto.getSharePageAns())
                .build();
    }
}
