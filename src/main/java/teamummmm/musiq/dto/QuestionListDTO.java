package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.ColorVal;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionListDTO {
    private Long question_id;  // 오브젝트 아이디 (userQuestionId)

    private String question_message;  // 질문 내용 (questionMsg)

    private String emoji;  // 질문 이모지 (emoji)

    private String main_color;  // 질문의 메인 컬러

    private List<ColorCount> color_count;  // 색상별 카운트

    @Builder
    @Data
    public static class ColorCount {
        private String color_name;  // 색상 이름
        private int count;  // 색상 count
    }

}
