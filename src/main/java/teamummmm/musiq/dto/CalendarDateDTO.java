package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.ColorVal;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarDateDTO {
    private ColorVal Color;  // 색깔

    private List<Music> musics;  // 색깔별 음악들

    @Builder
    @Data
    public static class Music {
        private String music_id;  // 음악 아이디
        private String music_name;  // 음악 제목
        private String artist_name;  // 아티스트 이름
        private String cover_url;  // 앨범커버 url
    }
}
