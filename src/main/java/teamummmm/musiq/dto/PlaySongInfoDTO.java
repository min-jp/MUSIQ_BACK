package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaySongInfoDTO {
    private String question_message;  // 질문 내용

    private Boolean caption_exists;  // 캡션 존재 여부

    private Music music;  // music 객체

    @Builder
    @Data
    public static class Music {
        private String music_id;  // 음악 아이디
        private Integer music_color;  // 음악의 색깔
        private String music_name;  // 음악 제목
        private String artist_name;  // 아티스트 이름
        private String cover_url;  // 앨범 커버 url
        private String music_url;  // 미리듣기 mp3파일 url
    }
}
