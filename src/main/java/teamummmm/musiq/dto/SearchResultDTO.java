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

    private String music_name;  // 음악 제목

    private String artist_name;  // 아티스트 이름

    private String cover_url;  // 앨범 커버 url
}
