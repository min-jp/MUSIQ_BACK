package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.QuestionIdDTO;
import teamummmm.musiq.model.CategoryVal;
import teamummmm.musiq.service.AdminService;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("admin")
public class AdminController {
    private final AdminService service;

    @PostMapping("/question")
    public ResponseEntity<?> adminAddQuestion(
            @RequestParam(value = "question_message") String questionMessage,
            @RequestParam(value = "emoji") String emoji,
            @RequestParam(value = "category") CategoryVal category,
            @RequestParam(value = "danceability") Float danceability,
            @RequestParam(value = "valence") Float valence,
            @RequestParam(value = "energy") Float energy,
            @RequestParam(value = "followup_question_id", required = false) Long followupQuestionId
            ) {
        try {
            QuestionIdDTO dto = service.addQuestion(questionMessage, emoji, category, danceability, valence, energy, followupQuestionId); // SpotifyLoginDTO 생성

            return ResponseEntity.ok().body(dto);  // SpotifyLoginDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @DeleteMapping("/question")
    public ResponseEntity<?> adminDeleteQuestion(
            @RequestParam(value = "question_id") Long questionId
    ) {
        try {
            service.deleteQuestion(questionId); // 삭제

            return ResponseEntity.ok().build();  // OK 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
