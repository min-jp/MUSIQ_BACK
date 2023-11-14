package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.*;
import teamummmm.musiq.dto.SearchAddSongDTO;
import teamummmm.musiq.dto.SearchResultDTO;
import teamummmm.musiq.model.*;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.MusicInfoRepository;
import teamummmm.musiq.repository.UserProfileRepository;
import teamummmm.musiq.repository.UserQuestionRepository;
import teamummmm.musiq.spotify.SpotifyService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class SearchService {
    private final SpotifyService spotifyService;
    private final AnswerRepository answerRepository;
    private final MusicInfoRepository musicInfoRepository;
    private final UserQuestionRepository userQuestionRepository;
    private final UserProfileRepository userProfileRepository;

    public List<SearchResultDTO> searchService (final String searchText) {  // 곡 검색
        Paging<Track> trackPaging = spotifyService.searchTracks(searchText);

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

        answerRepository.save(answerEntity);  // 저장

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

        updateAvgFeature(questionId);  // 사용자의 feature 업데이트

        ColorVal colorVal = answerRepository.findBestColor(questionId);  // 색상 업데이트

        return SearchAddSongDTO.builder().main_color(colorVal.ordinal()).build();  // SearchAddSongDTO 생성해서 리턴
    }

    private MusicInfoEntity setMusicInfo(final String musicId) {  // 음악 정보가 기존에 없는 경우
        AudioFeatures audioFeatures = spotifyService.getAudioFeatures(musicId);  // 음악 정보

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

        MusicInfoEntity newMusicInfo = MusicInfoEntity.builder().musicColor(colorVal).musicId(musicId).build();  // MusicInfoEntity 생성

        musicInfoRepository.save(newMusicInfo);  // 저장

        return newMusicInfo;
    }

    private void updateAvgFeature(final Long questionId) {  // 답변의 평균 Feature 계산
        List<AnswerEntity> answers = answerRepository.findByUserQuestion_UserQuestionId(questionId);  // 유저의 답변 모두 찾기

        Float sumDanceability = 0.0F;  // danceability의 합
        Float sumEnergy = 0.0F;  // energy의 합
        Float sumValence = 0.0F;  // valence의 합

        for (AnswerEntity answer : answers) {  // 각 변수의 합 구하기
            AudioFeatures audioFeatures = spotifyService.getAudioFeatures(answer.getMusicInfo().getMusicId());  // AudioFeatures 가져오기

            sumDanceability += audioFeatures.getDanceability();
            sumEnergy += audioFeatures.getEnergy();
            sumValence += audioFeatures.getValence();
        }

        Integer listSize = answers.size();  // answer 개수

        UserProfileEntity userProfileEntity = userQuestionRepository.findById(questionId).get().getUser();  // 기존 user 엔티티 찾기

        userProfileEntity.updateAudioFeatures(sumValence/listSize, sumEnergy/listSize, sumDanceability/listSize);  // 값 업데이트

        userProfileRepository.save(userProfileEntity);  // 저장
    }
}
