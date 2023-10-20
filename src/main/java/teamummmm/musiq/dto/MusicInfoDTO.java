package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.MusicInfoEntity;
import teamummmm.musiq.model.colorVal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MusicInfoDTO {
    private String musicId;  // 음악 아이디

    private colorVal musicColor;  // 음악별 색깔

    public MusicInfoDTO(final MusicInfoEntity entity) {
        this.musicId = entity.getMusicId();
        this.musicColor = entity.getMusicColor();
    }

    public static MusicInfoEntity toEntity(final MusicInfoDTO dto) {
        return MusicInfoEntity.builder()
                .musicId(dto.getMusicId())
                .musicColor(dto.getMusicColor())
                .build();
    }
}
