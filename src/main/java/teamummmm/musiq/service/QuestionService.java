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

    private final Long FIRST_CALL = -1L;

    public RequestQuestionDTO mainQuestionService(final Long userId, final boolean refresh, final Long thisQuestionId) {
        UserQuestionEntity entity;  // 엔티티 선언

        if (!answerRepository.existsByUserQuestion_User_UserId(userId)) {  // 처음 호출하는 경우
            entity = userQuestionRepository.defaultQuestionList(userId).get(0);  // 디폴트 질문 호출
        }
        else if (thisQuestionId.equals(FIRST_CALL)) {  // 앱을 다시 실행시키는 경우
            entity = findBestMainQuestion(userId);  // 사용자 값과 가장 가까운 메인 질문 찾기
        }
        else {  // 답한 질문이 있는 경우
            UserQuestionEntity prevQuestionEntity = userQuestionRepository.findById(thisQuestionId).get();  // 이전 질문 탐색

            if (prevQuestionEntity.getAnswerPageAnsList().isEmpty()) {  // 대답을 하지 않은 경우
                if (refresh) {  // 새로고침한 경우
                    prevQuestionEntity.updateRankingCount(prevQuestionEntity.getRankingCount() + 1);  // 랭킹 카운트 증가
                    userQuestionRepository.save(prevQuestionEntity); // 저장
                    entity = findBestMainQuestion(userId);  // 사용자 값과 가장 가까운 메인 질문 재탐색
                }
                else {  // 새로고침을 하지 않은 경우
                    entity = prevQuestionEntity;  // 기존 질문
                }
            }
            else {  // 대답을 한 경우
                CommonQuestionEntity commonQuestionEntity = prevQuestionEntity.getCommonQuestion().getFollowupQuestion();  // 후속 질문 엔티티

                if (commonQuestionEntity != null) {  // 후속 질문이 있는 경우
                    entity = userQuestionRepository.findByCommonQuestionAndUser_UserId(commonQuestionEntity, userId);  // 후속 질문을 가진 엔티티 탐색
                }
                else {  // 후속 질문이 없는 경우
                    entity = findBestMainQuestion(userId);  // 사용자 값과 가장 가까운 메인 질문 찾기
                }
            }
        }

        if (entity == null) {  // 대답을 안한 질문이 없는 경우
            return RequestQuestionDTO.builder().build();  // 빈 RequestQuestionDTO 리턴
        }

        return RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .main_color(ColorVal.DEFAULT.ordinal())
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .build();  // RequestQuestionDTO 리턴
    }

    public RequestQuestionDTO answeredQuestionService(final Long userId, final boolean refresh, final Long thisQuestionId, final Long otherQuestionId) {
        // 유저 아이디 받아서 질문 리턴 (유저질문)
        final List<UserQuestionEntity> entities = userQuestionRepository.findByUser_UserIdAndAnswerPageAnsListIsNotEmpty(userId);

        if (entities.isEmpty()) {  // 대답한 질문이 없는 경우
            return RequestQuestionDTO.builder().build();  // 빈 DTO 리턴
        }

        UserQuestionEntity entity;  // 엔티티

        // FIXME
        //  코드 수정.. 너무 잘못짰어...

        if (thisQuestionId.equals(FIRST_CALL)) {  // 앱을 다시 실행시키는 경우
            if (entities.size() == 1) {  // 한개인 경우
                entity = entities.get(0);
            }
            else if(otherQuestionId.equals(FIRST_CALL)) {  // 윗 질문 호출
                Random rand = new Random();
                entity = entities.get(rand.nextInt(entities.size()));  // 랜덤 호출
            }
            else {  // 아래 질문 호출
                entity = getUniqueEntity(otherQuestionId, entities);
            }
        }
        else {
            if (refresh) {  // 리프레시를 하는 경우
                List<UserQuestionEntity> temp_entities = new ArrayList<>(entities);  // 엔티티 복사
                for (UserQuestionEntity e : temp_entities) {  // otherQuestionId 찾아서 삭제
                    if (e.getUserQuestionId().equals(otherQuestionId)) {
                        temp_entities.remove(e);
                        break;
                    }
                }
                for (UserQuestionEntity e : temp_entities) {  // thisQuestionId 찾아서 삭제
                    if (e.getUserQuestionId().equals(thisQuestionId)) {
                        temp_entities.remove(e);
                        break;
                    }
                }

                if (temp_entities.isEmpty()){  // 총 2개였던 경우
                    entity = null;
                    for (UserQuestionEntity e : entities) {
                        if (e.getUserQuestionId().equals(thisQuestionId)) {  // 기존 질문 리턴
                            entity = e;
                            break;
                        }
                    }
                }
                else {  // 3개 이상인 경우
                    Random rand = new Random();
                    entity = temp_entities.get(rand.nextInt(temp_entities.size()));  // 랜덤 호출
                }
            }
            else {  // 리프레시를 하지 않는 경우
                entity = null;
                for (UserQuestionEntity e : entities) {
                    if (e.getUserQuestionId().equals(thisQuestionId)) {  // 기존 질문 리턴
                        entity = e;
                        break;
                    }
                }
                if (thisQuestionId.equals(otherQuestionId) && entities.size() == 2) {  // 새로운 질문이 추가됐을 때
                    entity = getUniqueEntity(otherQuestionId, entities);
                }
            }
        }

        // 리턴
        return RequestQuestionDTO.builder()
                .question_id(entity.getUserQuestionId())  // 오브젝트 아이디 (userQuestionId)
                .question_message(entity.getCommonQuestion().getQuestionMsg())  // 질문 내용 (questionMsg)
                .emoji(entity.getCommonQuestion().getEmoji())  // 질문 이모지 (emoji)
                .main_color(answerRepository.findBestColor(entity.getUserQuestionId()).ordinal())  // best color
                .build();
    }

    private UserQuestionEntity getUniqueEntity(Long otherQuestionId, List<UserQuestionEntity> entities) {  // 중복되지 않는 질문 찾기
        UserQuestionEntity entity;
        List<UserQuestionEntity> temp_entities = new ArrayList<>(entities);  // 엔티티 복사
        for (UserQuestionEntity e : temp_entities) {
            if (e.getUserQuestionId().equals(otherQuestionId)) {  // otherQuestionId 찾아서 삭제
                temp_entities.remove(e);
                break;
            }
        }

        Random rand = new Random();
        entity = temp_entities.get(rand.nextInt(temp_entities.size()));  // 랜덤 호출
        return entity;
    }

    // 사용자 값과 가장 가까운 메인 질문 찾기
    private UserQuestionEntity findBestMainQuestion(final Long userId) {
        List<UserQuestionEntity> entities = userQuestionRepository.mainQuestionList(userId);  // 대답 안한 UserQuestionEntity 찾기

        if (entities.isEmpty()) {  // 메인 질문이 없는 경우
            return null;  // null 리턴
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
