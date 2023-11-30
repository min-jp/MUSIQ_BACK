package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.service.QuestionService;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("question")
public class QuestionController {
    private final QuestionService service;  // service

    @GetMapping("/main")
    public ResponseEntity<?> mainQuestionRequest(
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "refresh", defaultValue = "false") boolean refresh,
            @RequestParam(value = "prev_quesiton_id", required = false) Long prevQuesitonId
    ) {
        try {
            RequestQuestionDTO dto = service.mainQuestionService(userId, refresh, prevQuesitonId);  // RequestQuestionDTO 생성

            return ResponseEntity.ok().body(dto);  // RequestQuestionDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @GetMapping("/answered")
    public ResponseEntity<?> answeredQuestionRequest(
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "refresh", defaultValue = "false") boolean refresh,
            @RequestParam(value = "prev_quesiton_id", required = false) Long prevQuesitonId
    ) {
        try {
            RequestQuestionDTO dto = service.answeredQuestionService(userId, refresh, prevQuesitonId);  // RequestQuestionDTO 생성

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

