package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.service.PlaySongService;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("play-song")
public class PlaySongController {
    private final PlaySongService service;

    @PostMapping("/caption")
    public ResponseEntity<?> captionController(
            @RequestParam(value = "caption_text") String captionText,
            @RequestParam(value = "answer_id") Long answerId
    ) {
        try {
            service.addCaptionService(captionText, answerId);

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
