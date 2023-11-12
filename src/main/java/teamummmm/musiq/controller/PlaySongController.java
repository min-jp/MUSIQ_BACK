package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.PlaySongInfoDTO;
import teamummmm.musiq.service.PlaySongService;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("play-song")
public class PlaySongController {
    private final PlaySongService service;

    @GetMapping("/play")
    private ResponseEntity<?> playSongController(
            @RequestParam(value = "answer_id") Long answerId
    ) {
        try {
            PlaySongInfoDTO dto = service.playSongService(answerId);  // PlaySongInfoDTO 생성

            return ResponseEntity.ok().body(dto);  // PlaySongInfoDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

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
