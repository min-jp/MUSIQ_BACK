package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.*;
import teamummmm.musiq.dto.SearchAddSongDTO;
import teamummmm.musiq.dto.SearchResultDTO;
import teamummmm.musiq.model.*;
import teamummmm.musiq.repository.*;
import teamummmm.musiq.spotify.SpotifySearchService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class SearchService {
    private final SpotifySearchService spotifySearchService;
    private final AnswerRepository answerRepository;
    private final MusicInfoRepository musicInfoRepository;
    private final UserQuestionRepository userQuestionRepository;
    private final UserProfileRepository userProfileRepository;
    private final CommonQuestionRepository commonQuestionRepository;

    public List<SearchResultDTO> searchService (final String searchText) {  // 곡 검색
        Paging<Track> trackPaging = spotifySearchService.searchTracks(searchText);

        validateTrack(trackPaging);  // 검색 결과가 비었는지 검사

        return Arrays.stream(trackPaging.getItems())  // stream 이용해서 변환 후 리턴
                .map(item -> {
                    ArtistSimplified artist = item.getArtists()[0];  // 아티스트
                    Image image = item.getAlbum().getImages()[0];  // 커버 이미지

                    return SearchResultDTO.builder()
                            .music_name(item.getName())
                            .music_id(item.getId())
                            .artist_name(artist.getName())
                            .cover_url(image.getUrl())
                            .build();  // dto 생성 후 리턴
                })
                .collect(Collectors.toList());
    }

    private void validateTrack(final Paging<Track> trackPaging) {  // 검색 결과 검사
        if (trackPaging == null) {
            throw new RuntimeException("Result is empty");
        }
    }

    public SearchAddSongDTO addSongService (final Long questionId, final String musicId) {
        // 음악 정보
        Optional<MusicInfoEntity> optionalMusicInfo = musicInfoRepository.findById(musicId);
        MusicInfoEntity musicInfo;

        // TODO
        //  같은 날짜에 같은 음악 추가하려고 하면 추가 안하도록 구현

        musicInfo = optionalMusicInfo.orElseGet(() -> setMusicInfo(musicId));  // 존재 여부에 따른 동작

        Optional<UserQuestionEntity> optionalUserQuestion = userQuestionRepository.findById(questionId); // 질문 정보

        AnswerEntity answerEntity = AnswerEntity.builder().answerDate(LocalDate.now(ZoneId.of("Asia/Seoul"))).musicInfo(musicInfo).userQuestion(optionalUserQuestion.get()).build(); // 답변 엔티티 생성

        answerEntity = answerRepository.save(answerEntity);  // 저장

        if (answerRepository.isFirstAnswerToday(questionId, LocalDate.now(ZoneId.of("Asia/Seoul")))) {  // 오늘 처음으로 질문에 답변을 추가한 경우
            Optional<UserQuestionEntity> optionalUpdateEntity = userQuestionRepository.findById(questionId);
            UserQuestionEntity updateEntity = optionalUpdateEntity.get();

            updateEntity.updateAnswerCount(updateEntity.getAnswerCount() + 1);  // answer 카운트 1 증가

            userQuestionRepository.save(updateEntity);  // 저장
        }

        if (answerRepository.isFirstAnswer(questionId)) {  // 질문에 처음으로 질문에 답변을 추가한 경우
            Optional<UserQuestionEntity> optionalUpdateEntity = userQuestionRepository.findById(questionId);
            UserQuestionEntity updateEntity = optionalUpdateEntity.get();

            updateEntity.updateRankingCount(0);  // 랭킹 카운트 초기화

            userQuestionRepository.save(updateEntity);  // 저장
        }

        updateAvgFeature(answerEntity);  // 사용자의 feature 업데이트

        ColorVal colorVal = answerRepository.findBestColor(questionId);  // 색상 업데이트

        return SearchAddSongDTO.builder().main_color(colorVal.ordinal()).build();  // SearchAddSongDTO 생성해서 리턴
    }

    private MusicInfoEntity setMusicInfo(final String musicId) {  // 음악 정보가 기존에 없는 경우
        AudioFeatures audioFeatures = spotifySearchService.getAudioFeatures(musicId);  // 음악 정보

        ColorVal colorVal;  // 색 정보

        Float danceability = audioFeatures.getDanceability();  // danceability
        Float energy = audioFeatures.getEnergy();  // energy
        Float valence = audioFeatures.getValence();  // valence

        // 컬러 결정 방법
        if (danceability < 0.5 && energy < 0.5 && valence < 0.5) {
            colorVal = ColorVal.VALUE1;
        } else if (danceability < 0.5 && energy < 0.5 && valence >= 0.5) {
            colorVal = ColorVal.VALUE2;
        } else if (danceability < 0.5 && energy >= 0.5 && valence < 0.5) {
            colorVal = ColorVal.VALUE3;
        } else if (danceability >= 0.5 && energy < 0.5 && valence < 0.5) {
            colorVal = ColorVal.VALUE4;
        } else if (danceability < 0.5 && energy >= 0.5 && valence >= 0.5) {
            colorVal = ColorVal.VALUE5;
        } else if (danceability >= 0.5 && energy < 0.5 && valence >= 0.5) {
            colorVal = ColorVal.VALUE6;
        } else if (danceability >= 0.5 && energy >= 0.5 && valence < 0.5) {
            colorVal = ColorVal.VALUE7;
        } else {
            colorVal = ColorVal.VALUE8;
        }

        MusicInfoEntity newMusicInfo = MusicInfoEntity.builder()
                .musicColor(colorVal)
                .musicId(musicId)
                .valence(valence)
                .energy(energy)
                .danceability(danceability)
                .build();  // MusicInfoEntity 생성

        newMusicInfo = musicInfoRepository.save(newMusicInfo);  // 저장

        return newMusicInfo;
    }

    private void updateAvgFeature(final AnswerEntity answer) {  // 답변의 평균 Feature 계산
        final Float musicDanceability = answer.getMusicInfo().getDanceability();  // 음악의 danceability
        final Float musicEnergy = answer.getMusicInfo().getEnergy();  // 음악의 energy
        final Float musicValence = answer.getMusicInfo().getValence();  // 음악의 valence

        // 유저별 평균 Feature 업데이트
        UserProfileEntity userProfileEntity = answer.getUserQuestion().getUser();  // 유저 프로필 엔티티

        Long listSize = answerRepository.countByUserQuestion_User_UserId(userProfileEntity.getUserId());  // 유저의 답변 개수

        Float userDanceability = userProfileEntity.getDanceability();  // danceability의 합
        Float userEnergy = userProfileEntity.getEnergy();  // energy의 합
        Float userValence = userProfileEntity.getValence();  // valence의 합

        userDanceability = (userDanceability * (listSize - 1) + musicDanceability) / listSize;
        userEnergy = (userEnergy * (listSize - 1) + musicEnergy) / listSize;
        userValence = (userValence * (listSize - 1) + musicValence) / listSize;  // 계산

        userProfileEntity.updateAudioFeatures(userValence, userEnergy, userDanceability);  // 값 업데이트

        userProfileRepository.save(userProfileEntity);  // 저장

        // 공통 질문별 평균 Feature 업데이트
        CommonQuestionEntity commonQuestionEntity = answer.getUserQuestion().getCommonQuestion();  // 공통 질문 엔티티

        listSize = answerRepository.countByUserQuestion_CommonQuestion_CommonQuestionId (commonQuestionEntity.getCommonQuestionId());  // 공통 질문의 총 답변 개수

        Float commonDanceability = commonQuestionEntity.getAvgDanceability();  // danceability의 합
        Float commonEnergy = commonQuestionEntity.getAvgEnergy();  // energy의 합
        Float commonValence = commonQuestionEntity.getAvgValence();  // valence의 합

        commonDanceability = (commonDanceability * (listSize - 1) + musicDanceability) / listSize;
        commonEnergy = (commonEnergy * (listSize - 1) + musicEnergy) / listSize;
        commonValence = (commonValence * (listSize - 1) + musicValence) / listSize;  // 계산

        commonQuestionEntity.updateAvgAudioFeatures(commonValence, commonEnergy, commonDanceability);  // 값 업데이트

        commonQuestionRepository.save(commonQuestionEntity);  // 저장
    }
}
