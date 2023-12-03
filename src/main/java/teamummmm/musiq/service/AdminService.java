package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.QuestionIdDTO;
import teamummmm.musiq.model.CategoryVal;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.UserProfileEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.CommonQuestionRepository;
import teamummmm.musiq.repository.UserProfileRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class AdminService {
    private final CommonQuestionRepository commonQuestionRepository;
    private final UserQuestionRepository userQuestionRepository;
    private final UserProfileRepository userProfileRepository;

    public QuestionIdDTO addQuestion(final String questionMessage,
                                     final String emoji,
                                     final CategoryVal category,
                                     final Float danceability,
                                     final Float valence,
                                     final Float energy,
                                     final Long followupQuestionId){
        CommonQuestionEntity commonQuestionEntity = CommonQuestionEntity.builder()
                .questionMsg(questionMessage)
                .emoji(emoji)
                .category(category)
                .danceability(danceability)
                .valence(valence)
                .energy(energy)
                .build();  // CommonQuestionEntity 생성

        if (followupQuestionId != null) {  // followupQuestionId가 존재하는 경우
            commonQuestionEntity.updateFollowupQuestion(commonQuestionRepository.findById(followupQuestionId).get());  // FollowupQuestion 추가
        }

        commonQuestionEntity = commonQuestionRepository.save(commonQuestionEntity);  // CommonQuestionEntity 저장

        List<UserProfileEntity> userProfileEntities = userProfileRepository.findAll();  // 모든 유저 가져옴

        for (UserProfileEntity userProfileEntity : userProfileEntities) {  // 유저별로 CommonQuestion 추가
            UserQuestionEntity userQuestionEntity = UserQuestionEntity.builder()
                    .user(userProfileEntity)
                    .commonQuestion(commonQuestionEntity)
                    .build();  // UserQuestionEntity 생성

            userQuestionRepository.save(userQuestionEntity);  // UserQuestionEntity 저장
        }

        return QuestionIdDTO.builder()
                .question_id(commonQuestionEntity.getCommonQuestionId())
                .build();  // QuestionIdDTO 생성 후 리턴
    }

    public void deleteQuestion(final Long questionId) {
        List<UserQuestionEntity> userQuestionEntities = userQuestionRepository.findByCommonQuestion_CommonQuestionId(questionId);  // CommonQuestionId로 모든 유저별 질문 리스트

        userQuestionRepository.deleteAllInBatch(userQuestionEntities);  // 유저별 CommonQuestion 삭제

        commonQuestionRepository.delete(commonQuestionRepository.findById(questionId).get());  // CommonQuestion 삭제
    }
}
