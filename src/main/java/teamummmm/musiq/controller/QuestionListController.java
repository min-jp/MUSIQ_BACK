package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.QuestionAnswerDTO;
import teamummmm.musiq.dto.QuestionListDTO;
import teamummmm.musiq.service.QuestionListService;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("question-list")
public class QuestionListController {
    private final QuestionListService service;

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestionList(
            @RequestParam(value = "user_id") Long userId
    ) {
        try {
            List<QuestionListDTO> dtos = service.questionListService(userId);  // List<QuestionListDTO> 생성

            return ResponseEntity.ok().body(dtos);  // RequestQuestionDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @GetMapping("/answers")
    public ResponseEntity<?> getAnswerList(
            @RequestParam(value = "question_id") Long questionId
    ) {
        try {
            QuestionAnswerDTO dto = service.answerListService(questionId);  // QuestionAnswerDTO 생성

            return ResponseEntity.ok().body(dto);  // RequestQuestionDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
