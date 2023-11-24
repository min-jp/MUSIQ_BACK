package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.User;
import teamummmm.musiq.dto.CodeLoginDTO;
import teamummmm.musiq.dto.SpotifyLoginDTO;
import teamummmm.musiq.model.CommonQuestionEntity;
import teamummmm.musiq.model.UserProfileEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.CommonQuestionRepository;
import teamummmm.musiq.repository.UserProfileRepository;
import teamummmm.musiq.repository.UserQuestionRepository;
import teamummmm.musiq.spotify.SpotifyAuthService;

import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class AuthService {
    private final SpotifyAuthService spotifyAuthService;
    private final UserProfileRepository userProfileRepository;
    private final UserQuestionRepository userQuestionRepository;
    private final CommonQuestionRepository commonQuestionRepository;

    public SpotifyLoginDTO spotifyLoginService() {  // 스포티파이 로그인
        return SpotifyLoginDTO.builder()
                .uri(spotifyAuthService.spotifyAuthCodeUri())  // spotifyAuthCodeUri 이용해 로그인 URI 가져옴
                .build();  // SpotifyLoginDTO 생성 후 리턴
    }

    public CodeLoginDTO codeLoginService(final String code) {  // code를 이용해 로그인 (회원가입)
        SpotifyApi spotifyApi = spotifyAuthService.spotifyAuthCode(code);  // token 정보
        User user = spotifyAuthService.getSpotifyUser(spotifyApi);  // 스포티파이 유저 정보
        Long userId;  // 유저 아이디

        final String spotifyId = user.getId();

        if (userProfileRepository.existsByLoginId(spotifyId)) {  // 사용자 존재하면 로그인
            userId = userProfileRepository.findByLoginId(spotifyId).getUserId();  // 유저 아이디 검색
        }
        else {  // 사용자 존재하지 않으면 회원가입
            UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                    .loginId(spotifyId)
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

            userId = savedEntity.getUserId();  // 유저 아이디
        }

        return CodeLoginDTO.builder()
                .token(spotifyApi.getAccessToken())
                .user_id(userId)
                .build();  // CodeLoginDTO 생성 후 리턴
    }
}
