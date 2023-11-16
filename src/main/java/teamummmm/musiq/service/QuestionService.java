package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.model.*;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.UserProfileRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.*;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionService {
    private final UserQuestionRepository userQuestionRepository;
    private final AnswerRepository answerRepository;
    private final UserProfileRepository userProfileRepository;

    private int recentCall = 0;  // 임시 랜덤 함수

    public RequestQuestionDTO mainQuestionService(final Long userId, final boolean refresh) {
        UserQuestionEntity entity;  // 엔티티 선언

        if (!answerRepository.existsByUserQuestion_User_UserId(userId)) {  // 처음 호출하는 경우
            entity = userQuestionRepository.defaultQuestionList(userId).get(0);  // 디폴트 질문 호출
        }
        else {  // 답한 질문이 있는 경우
            if (refresh) {  // 새로고침한 경우
                entity = findBestMainQuestion(userId);  // 기존 best 메인 질문
                entity.updateRankingCount(entity.getRankingCount() + 1);  // 랭킹 카운트 증가
                userQuestionRepository.save(entity); // 저장 후 다시 best 메인 질문 탐색
            }

            entity = findBestMainQuestion(userId);  // 사용자 값과 가장 가까운 메인 질문 찾기

            // TODO
            //  후속질문 출력하도록 로직 수정
        }

        return RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .main_color(ColorVal.DEFAULT.ordinal())
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .build();  // RequestQuestionDTO 리턴
    }

    public RequestQuestionDTO answeredQuestionService(final Long userId, final boolean refresh) {
        // 유저 아이디 받아서 질문 리턴 (유저질문)
        List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserIdAndAnswerPageAnsListIsNotEmpty(userId);

        if (entities.isEmpty()) {  // 대답한 질문이 없는 경우
            return RequestQuestionDTO.builder().build();  // 빈 DTO 리턴
        }

        // TODO
        //  리프레시에 따른 로직 구현

        // 엔티티 중 선택
        recentCall += 1;
        if (recentCall >= entities.size()) {
            recentCall = 0;
        }
        UserQuestionEntity entity = entities.get(recentCall);  // 랜덤 선택

        // 리턴
        return RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .main_color(answerRepository.findBestColor(entity.getUserQuestionId()).ordinal())  // best color
                .build();
    }

    // 사용자 값과 가장 가까운 메인 질문 찾기
    private UserQuestionEntity findBestMainQuestion(final Long userId) {
        List<UserQuestionEntity> entities = userQuestionRepository.mainQuestionList(userId);  // 대답 안한 UserQuestionEntity 찾기

        if (entities.isEmpty()) {  // 메인 질문이 없는 경우
            return UserQuestionEntity.builder().build();  // 빈 DTO 리턴
        }

        // 유저의 파라미터 찾기
        UserProfileEntity userProfileEntity = userProfileRepository.findById(userId).get();  // user 엔티티 찾기
        final Float userDanceability = userProfileEntity.getDanceability();  // 유저의 danceability
        final Float userEnergy = userProfileEntity.getEnergy();  // 유저의 energy
        final Float userValence = userProfileEntity.getValence();  // 유저의 valence

        // 유클리드 거리 계산해서 거리가 가장 가까운 질문 찾기
        return entities.stream().min(Comparator.comparingDouble(entity -> {  // 가장 작은 값 찾기
            CommonQuestionEntity commonQuestion = entity.getCommonQuestion();  // feature를 위한 CommonQuestionEntity

            return (Math.log10(entity.getRankingCount() + 1)/Math.log10(3) +  // 랭킹 카운트에 따라 log3(x+1)의 크기로 거리에 더함
                    Math.sqrt(  // 루트
                            Math.pow(userDanceability - commonQuestion.getDanceability(), 2) +  // danceability 값의 차의 제곱
                            Math.pow(userEnergy - commonQuestion.getEnergy(), 2) +  // energy 값의 차의 제곱
                            Math.pow(userValence - commonQuestion.getValence(), 2)  // valence 값의 차의 제곱
                    ));  // 유클리드 거리 계산
        })).orElse(null);  // 가장 가까운 UserQuestionEntity 찾은 뒤 리턴
    }
}
