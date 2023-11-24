package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.CodeLoginDTO;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.SpotifyLoginDTO;
import teamummmm.musiq.service.AuthService;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("auth")
public class AuthController {
    private final AuthService service;

    // callback 테스트 함수
    @GetMapping("/callback")
    public void spotifyCallback(
            @RequestParam(value = "code") String code
    ) {
        System.out.println(code);
    }

    @GetMapping("/spotify-login")
    public ResponseEntity<?> spotifyLoginController() {
        try {
            SpotifyLoginDTO dto = service.spotifyLoginService(); // SpotifyLoginDTO 생성

            return ResponseEntity.ok().body(dto);  // SpotifyLoginDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @GetMapping("/code-login")
    public ResponseEntity<?> codeLoginController(
            @RequestParam(value = "code") String code
    ) {
        try {
            CodeLoginDTO dto = service.codeLoginService(code); // CodeLoginDTO 생성

            return ResponseEntity.ok().body(dto);  // CodeLoginDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
