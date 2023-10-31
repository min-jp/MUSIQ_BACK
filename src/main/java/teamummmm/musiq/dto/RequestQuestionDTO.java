package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestQuestionDTO {
    private Long userQuestionId;  // 오브젝트 아이디

    private String questionMsg;  // 질문 내용

    private String emoji;  // 질문 이모지

    private String mainColor;  // 질문의 메인 컬러
}
