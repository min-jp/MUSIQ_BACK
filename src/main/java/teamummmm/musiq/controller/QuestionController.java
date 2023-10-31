package teamummmm.musiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ResponseDTO;
import teamummmm.musiq.dto.UserQuestionDTO;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.service.QuestionService;
import teamummmm.musiq.service.testService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private testService service;  //test

    @GetMapping("/main")
    public ResponseEntity<?> mainQuestionRequest(
            @RequestParam("user_id") Long id,
            @RequestParam("refresh") boolean refresh
    ) {
        try {
            // TODO
            //  dtos에 테스트 서비스 넣음
            List<UserQuestionEntity> entities = service.test();  // test
            List<UserQuestionDTO> dtos = entities.stream().map(UserQuestionDTO::new).collect(Collectors.toList());  // UserQuestionDTO 변환
            ResponseDTO<UserQuestionDTO> response = ResponseDTO.<UserQuestionDTO>builder().data(dtos).build();  // UserQuestionDTO 이용하여 ResponseDTO 초기화
            return ResponseEntity.ok().body(response);  // ResponseDTO 리턴
        } catch (Exception e) {
            String error = e.getMessage();
            // TODO
            //  String 자료형 수정
            ResponseDTO<UserQuestionDTO> response = ResponseDTO.<UserQuestionDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}

