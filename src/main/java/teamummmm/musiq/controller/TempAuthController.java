package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.UserProfileDTO;
import teamummmm.musiq.service.TempAuthService;

// FIXME
//  auth 구현
@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("auth")
public class TempAuthController {
    private final TempAuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam(value = "login_id") String loginId
    ) {
        try {
            UserProfileDTO dto = service.registerService(loginId);

            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
