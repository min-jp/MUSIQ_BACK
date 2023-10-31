package teamummmm.musiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.dto.ResponseDTO;
import teamummmm.musiq.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService service;  // service

    @GetMapping("/main")
    public ResponseEntity<?> mainQuestionRequest(
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "refresh") boolean refresh
    ) {
        try {
            // TODO
            //  유저 아이디
            Long temporaryUserId = 12345L;  // 임시 유저 아이디
            List<RequestQuestionDTO> dtos = service.mainQuestionService(temporaryUserId, refresh);  // UserQuestionDTO 생성
            ResponseDTO<RequestQuestionDTO> response = ResponseDTO.<RequestQuestionDTO>builder()
                    .data(dtos)
                    .build();  // UserQuestionDTO 이용하여 ResponseDTO 초기화
            return ResponseEntity.ok().body(response);  // ResponseDTO 리턴
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<RequestQuestionDTO> response = ResponseDTO.<RequestQuestionDTO>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}

