package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionAnswerDTO {
    private List<ColorCount> color_counts;  // 색상별 카운트

    private List<AnswerDate> answer_dates;  // 날짜별 답변

    @Builder
    @Data
    public static class ColorCount {
        private int color_name;  // 색상 이름
        private Long count;  // 색상 count
    }

    @Builder
    @Data
    public static class AnswerDate {
        private LocalDate answer_date;  // 답변 날짜 (answerDate)
        private int day_color;  // 그날의 색깔
        private List<Answer> answers;  // 답변들

        @Builder
        @Data
        public static class Answer {
            private Long answer_id;  // 오브젝트 아이디 (answerId)
            private String caption;  // 캡션 (caption)
            private Music music;  // 음악

            @Builder
            @Data
            public static class Music {
                private String music_id;  // 음악 아이디 (musicId)
                private int music_color;  // 음악별 색깔 (musicColor)
                private String music_name;  // 음악 제목
                private String artist_name;  // 아티스트 이름
                private String cover_url;  // 앨범 커버 url
            }
        }
    }
}
