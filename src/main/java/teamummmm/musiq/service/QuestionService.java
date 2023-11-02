package teamummmm.musiq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.CommonQuestionRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.List;

@Service
@AllArgsConstructor  // 생성자 주입
public class QuestionService {
    private UserQuestionRepository userQuestionRepository;
    private CommonQuestionRepository commonQuestionRepository;

    public RequestQuestionDTO mainQuestionService(Long userId, boolean refresh) {
        // 유저 아이디 받아서 질문 리턴 (유저질문)
        List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserId(userId);

        // TODO
        //  리프레시에 따른 로직 구현
        // 리프레시 여부 확인
        if (refresh) {  // 일반 질문 호출

        }
        else {  // 새로고침한 질문 호출

        }

        // TODO
        //  선택 코드 위 로직 안에 넣기
        // 엔티티 중 선택
        UserQuestionEntity entity = entities.get(0);

        // TODO
        //  데이터 적재 코드 재사용
        // 데이터 적재 코드
        /*
        List<RequestQuestionDTO> dtos = entities.stream()  // stream 이용해서 변환
                .map(entity -> {
                    RequestQuestionDTO dto = RequestQuestionDTO.builder()
                            .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                            .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                            .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());
        */

        // 데이터 적재 코드
        RequestQuestionDTO dto = RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .build();

        // 리턴
        return dto;
    }

    // TODO
    //  메인 질문 리프레시 질문 구현
    // 메인 리프레시 질문

    // TODO
    //  메인 질문 일반 질문 구현
    // 메인 일반 질문

    public RequestQuestionDTO answeredQuestionService(Long userId, boolean refresh) {
        // 유저 아이디 받아서 질문 리턴 (유저질문)
        List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserId(userId);

        // TODO
        //  리프레시에 따른 로직 구현
        // 리프레시 여부 확인
        if (refresh) {  // 일반 질문 호출

        }
        else {  // 새로고침한 질문 호출

        }

        // TODO
        //  선택 코드 위 로직 안에 넣기
        // 엔티티 중 선택
        UserQuestionEntity entity = entities.get(0);

        // 데이터 적재 코드
        RequestQuestionDTO dto = RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .build();

        // 리턴
        return dto;
    }

    // TODO
    //  대답한 질문 리프레시 질문 구현
    // 대답한 리프레시 질문

    // TODO
    //  대답한 질문 일반 질문 구현
    // 대답한 일반 질문
}
