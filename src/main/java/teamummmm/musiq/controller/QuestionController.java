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
            @RequestParam(value = "refresh") boolean refresh
    ) {
        try {
            // TODO
            //  유저 아이디
            Long temporaryUserId = 1L;  // 임시 유저 아이디

            RequestQuestionDTO dto = service.mainQuestionService(temporaryUserId, refresh);  // RequestQuestionDTO 생성

            return ResponseEntity.ok().body(dto);  // RequestQuestionDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @GetMapping("/answered")
    public ResponseEntity<?> answeredQuestionRequest(
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "refresh") boolean refresh
    ) {
        try {
            // TODO
            //  유저 아이디
            Long temporaryUserId = 1L;  // 임시 유저 아이디

            RequestQuestionDTO dto = service.answeredQuestionService(temporaryUserId, refresh);  // RequestQuestionDTO 생성

            return ResponseEntity.ok().body(dto);  // RequestQuestionDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}

