package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionService {
    private final UserQuestionRepository userQuestionRepository;
    private final AnswerRepository answerRepository;

    public RequestQuestionDTO mainQuestionService(final Long userId, final boolean refresh) {
        // 유저 아이디 받아서 질문 리턴 (유저질문)
        List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserIdAndAnswerPageAnsListIsEmpty(userId);

        // 현재 유저의 대답 중 가장 가장 맞는 대답 찾기

        // 그 중에 가장 콜이 적은 대답 찾기

        // TODO
        //  리프레시에 따른 로직 구현
        // 리프레시 여부 확인
        if (refresh) {  // 새로고침한 질문 호출

        }
        else {  // 일반 질문 호출

        }

        // TODO
        //  선택 코드 위 로직 안에 넣기
        // 엔티티 중 선택
        Random random = new Random();
        int randomNum = random.nextInt(entities.size());
        UserQuestionEntity entity = entities.get(randomNum);

        // 콜 수 업데이트

        // 데이터 적재 코드

        // 리턴
        return RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .build();
    }

    // TODO
    //  메인 질문 리프레시 질문 구현
    // 메인 리프레시 질문

    // TODO
    //  메인 질문 일반 질문 구현
    // 메인 일반 질문

    public RequestQuestionDTO answeredQuestionService(final Long userId, final boolean refresh) {
        // 유저 아이디 받아서 질문 리턴 (유저질문)
        List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserIdAndAnswerPageAnsListIsNotEmpty(userId);

        if (entities.isEmpty()) {  // 대답한 질문이 없는 경우
            return RequestQuestionDTO.builder().build();  // 빈 DTO 리턴
        }

        // TODO
        //  리프레시에 따른 로직 구현
        // 리프레시 여부 확인
        if (refresh) {  // 새로고침한 질문 호출

        }
        else {  // 일반 질문 호출

        }

        // TODO
        //  선택 코드 위 로직 안에 넣기
        // 엔티티 중 선택
        Random random = new Random();
        int randomNum = random.nextInt(entities.size());
        UserQuestionEntity entity = entities.get(randomNum);

        // 콜 수 업데이트

        // 데이터 적재 코드

        // 리턴
        return RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .main_color(answerRepository.findBestColor(entity.getUserQuestionId()))  // best color
                .build();
    }

    // TODO
    //  대답한 질문 리프레시 질문 구현
    // 대답한 리프레시 질문

    // TODO
    //  대답한 질문 일반 질문 구현
    // 대답한 일반 질문
}
