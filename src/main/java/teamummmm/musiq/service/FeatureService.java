package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.CommonQuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class FeatureService {
    private final AnswerRepository answerRepository;
    private final CommonQuestionRepository commonQuestionRepository;

    //@Scheduled(fixedRate = 3540000)  // 59분마다 실행
    public void validateCommonFeature() {  // 공통 질문별 특징 검사
        List<CommonQuestionEntity> commonQuestionEntities = commonQuestionRepository.findAll();  // 전체 공통 질문

        for (CommonQuestionEntity commonQuestionEntity : commonQuestionEntities) {
            final List<AnswerEntity> answerEntities = answerRepository.findByUserQuestion_CommonQuestion_CommonQuestionId(commonQuestionEntity.getCommonQuestionId());  // 공통 질문별 답변

            if (answerEntities.isEmpty()) {  // 답변을 하지 않은 경우
                continue;
            }

            Float danceability = 0.0f;  // danceability
            Float energy = 0.0f;  // energy
            Float valence = 0.0f;  // valence

            for (AnswerEntity answerEntity : answerEntities) {
                danceability += answerEntity.getMusicInfo().getDanceability();
                energy += answerEntity.getMusicInfo().getEnergy();
                valence += answerEntity.getMusicInfo().getValence();
            }  // 합

            int size_num = answerEntities.size();  // 답변 개수

            danceability /= size_num;
            energy /= size_num;
            valence /= size_num;  // 나누기

            commonQuestionEntity.updateAvgAudioFeatures(valence, energy, danceability);  // 업데이트

            commonQuestionRepository.save(commonQuestionEntity);  // 저장
        }
    }

    //@Scheduled(fixedRate = 3540000)  // 59분마다 실행
    public void validateUserFeature() {  // 유저별 특징 검사

    }
}
