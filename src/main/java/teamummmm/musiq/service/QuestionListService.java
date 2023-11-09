package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.QuestionAnswerDTO;
import teamummmm.musiq.dto.QuestionListDTO;
import teamummmm.musiq.model.ColorVal;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionListService {
    private final UserQuestionRepository userQuestionRepository;
    private final AnswerRepository answerRepository;

    public List<QuestionListDTO> questionListService(Long userId) {
        List<UserQuestionEntity> entities =  userQuestionRepository.findByUser_UserId(userId);

        return entities.stream()
                .map(entity -> {
                    CommonQuestionEntity commonQuestion = entity.getCommonQuestion();  // 공통 질문 내용

                    return QuestionListDTO.builder()
                            .question_id(entity.getUserQuestionId())
                            .question_message(commonQuestion.getQuestionMsg())
                            .emoji(commonQuestion.getEmoji())
                            .main_color(answerRepository.findBestColor(entity.getUserQuestionId()))  // best color 찾기
                            //.color_count(answerRepository.countMusicColorsByUserQuestionId(entity.getUserQuestionId()))
                            .color_count(colorCountCal(entity))  // 컬러 비율 계산
                            .build();
                })
                .toList();
    }

    private List<QuestionListDTO.ColorCount> colorCountCal(UserQuestionEntity entity) {  // 컬러 비율 계산
        List<Object[]> objects = answerRepository.countMusicColorsByUserQuestionId(entity.getUserQuestionId());

        return objects.stream().map(object -> QuestionListDTO.ColorCount.builder()
                .color_name((ColorVal) object[0])  // 컬러 이름
                .count((Long) object[1])  // count
                .build()).toList();
    }

    public List<QuestionAnswerDTO> answerListService(Long questionId) {
        return new ArrayList<>();
    }
}
