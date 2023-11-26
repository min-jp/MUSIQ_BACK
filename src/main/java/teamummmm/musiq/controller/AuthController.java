package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamummmm.musiq.dto.CodeDTO;
import teamummmm.musiq.dto.CodeLoginDTO;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.SpotifyLoginDTO;
import teamummmm.musiq.service.AuthService;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("auth")
public class AuthController {
    private final AuthService service;

    @GetMapping("/spotify-login")
    public ResponseEntity<?> spotifyLoginController(
            @RequestParam(value = "redirect_uri") String redirectUri
    ) {
        try {
            SpotifyLoginDTO dto = service.spotifyLoginService(redirectUri); // SpotifyLoginDTO 생성

            return ResponseEntity.ok().body(dto);  // SpotifyLoginDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @PostMapping("/code-login")
    public ResponseEntity<?> codeLoginController(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "redirect_uri") String redirectUri
    ) {
        try {
            CodeLoginDTO dto = service.codeLoginService(code, redirectUri); // CodeLoginDTO 생성

            return ResponseEntity.ok().body(dto);  // CodeLoginDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @GetMapping("/callback")
    public ResponseEntity<?> spotifyCallback(
            @RequestParam(value = "code") String code
    ) {
        try {
            CodeDTO dto = service.callbackService(code); // CodeDTO 생성

            return ResponseEntity.ok().body(dto);  // CodeDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
