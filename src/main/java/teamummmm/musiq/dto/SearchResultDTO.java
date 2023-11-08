package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResultDTO {
    private String music_id;  // 음악 아이디 (musicId)

    private String music_name;

    private String artist_name;

    private String cover_url;
}
