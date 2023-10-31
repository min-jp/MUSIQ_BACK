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
public class QuestionListDTO {
    private Long userQuestionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    private String mainColor;  // 질문의 메인 컬러

    private List<ColorCount> colorCount;  // 색상별 카운트

    private List<AnswerDate> answerDates;  // 날짜별 질문

    @Builder
    @Data
    public static class ColorCount {
        private String colorName;
        private int count;
    }

    @Builder
    @Data
    public static class AnswerDate {
        private LocalDate answerDate;
        private String dayColor;
        private List<Answer> answers;

        @Builder
        @Data
        public static class Answer {
            private Long answerId;
            private String caption;
            private Music music;

            @Builder
            @Data
            public static class Music {
                private String musicId;
                private String musicColor;
            }
        }
    }

}
