package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.UserProfileDTO;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.UserProfileEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.CommonQuestionRepository;
import teamummmm.musiq.repository.UserProfileRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class TempAuthService {
    private final UserProfileRepository userProfileRepository;
    private final UserQuestionRepository userQuestionRepository;
    private final CommonQuestionRepository commonQuestionRepository;

    public UserProfileDTO registerService(final String loginId) {
        if (userProfileRepository.existsByLoginId(loginId)) {  // 로그인 정보 확인
            throw new RuntimeException("Username already exists");  // 존재하면 오류
        }

        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .loginId(loginId)
                .build();  // UserProfileEntity 생성

        UserProfileEntity savedEntity = userProfileRepository.save(userProfileEntity);  // 저장

        // 유저 질문 생성
        List<CommonQuestionEntity> commonQuestionEntityList = commonQuestionRepository.findAll();
        commonQuestionEntityList.forEach(commonQuestionEntity -> {
            UserQuestionEntity userQuestionEntity = UserQuestionEntity.builder()
                    .user(savedEntity)
                    .commonQuestion(commonQuestionEntity)
                    .build();  // UserQuestionEntity 생성

            userQuestionRepository.save(userQuestionEntity);  //저장
        });

        return UserProfileDTO.builder().user_id(savedEntity.getUserId()).build();  // 리턴
    }
}
