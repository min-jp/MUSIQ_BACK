package teamummmm.musiq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.CommonQuestionRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor  // 생성자 주입
public class QuestionService {
    private UserQuestionRepository userQuestionRepository;
    private CommonQuestionRepository commonQuestionRepository;

    // TODO
    //  dto 하나만 리턴하도록 변경
    public List<RequestQuestionDTO> mainQuestionService(Long userId, boolean refresh) {
        // TODO
        //  리프레시에 따른 로직 구현
        // 리프레시 여부 확인
        if (refresh) {  // 일반 질문 호출

        }
        else {  // 새로고침한 질문 호출

        }

        // 유저 아이디 받아서 질문 리턴 (유저질문)
        List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserId(userId);

        // 데이터 적재
        List<RequestQuestionDTO> dtos = entities.stream()  // stream 이용해서 변환
                .map(entity -> {
                    RequestQuestionDTO dto = RequestQuestionDTO.builder()
                            .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                            .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                            .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                            // TODO
                            //  main_color 로직 구현
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());

        // 리턴
        return dtos;
    }

    // TODO
    //  리프레시 질문 구현
    // 리프레시 질문

    // TODO
    //  일반 질문 구현
    // 일반 질문
}
