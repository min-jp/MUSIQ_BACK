package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.*;
import teamummmm.musiq.dto.SearchAddSongDTO;
import teamummmm.musiq.dto.SearchResultDTO;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.ColorVal;
import teamummmm.musiq.model.MusicInfoEntity;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.MusicInfoRepository;
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

        if (answerRepository.isFirstAnswer(questionId, LocalDate.now(ZoneId.of("Asia/Seoul")))) {  // 오늘 처음으로 질문에 답변을 추가한 경우
            Optional<UserQuestionEntity> optionalUpdateEntity = userQuestionRepository.findById(questionId);
            UserQuestionEntity updateEntity = optionalUpdateEntity.get();

            updateEntity.updateAnswerCount(updateEntity.getAnswerCount() + 1);  // 카운트 1 증가

            userQuestionRepository.save(updateEntity);  // 저장
        }



        ColorVal colorVal = answerRepository.findBestColor(questionId);  // 색상 업데이트

        return SearchAddSongDTO.builder().main_color(colorVal.ordinal()).build();  // SearchAddSongDTO 생성해서 리턴
    }

    private MusicInfoEntity setMusicInfo(final String musicId) {  // 음악 정보가 기존에 없는 경우
        AudioFeatures audioFeatures = spotifyService.getAudioFeatures(musicId);  // 음악 정보

        ColorVal colorVal;  // 색 정보

        float danceability = audioFeatures.getDanceability();  // danceability
        float energy = audioFeatures.getEnergy();  // energy
        float valence = audioFeatures.getValence();  // valence

        // FIXME
        //  컬러 결정 방법
        if (danceability < 0.5) {
            colorVal = ColorVal.VALUE1;
        }
        else {
            colorVal = ColorVal.VALUE2;
        }

        MusicInfoEntity newMusicInfo = MusicInfoEntity.builder().musicColor(colorVal).musicId(musicId).build();  // MusicInfoEntity 생성

        musicInfoRepository.save(newMusicInfo);  // 저장

        return newMusicInfo;
    }
}
