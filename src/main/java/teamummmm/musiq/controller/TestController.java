package teamummmm.musiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.dto.QuestionListDTO;
import teamummmm.musiq.service.TestService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestService service;

    @GetMapping
    public ResponseEntity<?> testRequest() {
        try {
            List<QuestionListDTO> dtos = new ArrayList<>();

            QuestionListDTO.ColorCount cnt = QuestionListDTO.ColorCount.builder().build();
            List<QuestionListDTO.ColorCount> l = new ArrayList<>();
            l.add(cnt);

            QuestionListDTO dto = QuestionListDTO.builder()
                    .color_count(l)
                    .build();

            dtos.add(dto);

            return ResponseEntity.ok().body(dtos);  // ResponseDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
