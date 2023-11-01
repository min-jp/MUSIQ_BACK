package teamummmm.musiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.QuestionListDTO;
import teamummmm.musiq.dto.ResponseDTO;
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
            ResponseDTO<QuestionListDTO> response = ResponseDTO.<QuestionListDTO>builder().data(dtos).build();  // UserQuestionDTO 이용하여 ResponseDTO 초기화
            return ResponseEntity.ok().body(response);  // ResponseDTO 리턴
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<QuestionListDTO> response = ResponseDTO.<QuestionListDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
