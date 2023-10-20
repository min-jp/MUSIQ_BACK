package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.OtherUserEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OtherUserDTO {
    private Long userId;  // 오브젝트 아이디

    private String nickname;  // 타 사용자 별명

    public OtherUserDTO(final OtherUserEntity entity) {
        this.userId = entity.getUserId();
        this.nickname = entity.getNickname();
    }

    public static OtherUserEntity toEntity(final OtherUserDTO dto) {
        return OtherUserEntity.builder()
                .userId(dto.getUserId())
                .nickname(dto.getNickname())
                .build();
    }
}
