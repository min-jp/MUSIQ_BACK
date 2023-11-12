package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;
import teamummmm.musiq.dto.PlaySongInfoDTO;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.spotify.SpotifyService;

import java.util.Optional;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class PlaySongService {
    private final AnswerRepository repository;
    private final SpotifyService spotifyService;

    public PlaySongInfoDTO playSongService(final Long answerId) {
        Optional<AnswerEntity> entity = repository.findById(answerId);  // 아이디로 entity 찾기
        AnswerEntity answer = entity.get();

        Track track = spotifyService.getTrack(answer.getMusicInfo().getMusicId());  // track 정보 검색

        ArtistSimplified artist = track.getArtists()[0];  // 아티스트
        Image image = track.getAlbum().getImages()[0];  // 커버 이미지

        PlaySongInfoDTO.Music music = PlaySongInfoDTO.Music.builder()
                .music_id(answer.getMusicInfo().getMusicId())
                .music_color(answer.getMusicInfo().getMusicColor())
                .music_name(track.getName())
                .artist_name(artist.getName())
                .cover_url(image.getUrl())
                .music_url(track.getPreviewUrl())
                .build();  // Music 객체 생성

        return PlaySongInfoDTO.builder()
                .question_message(answer.getUserQuestion().getCommonQuestion().getQuestionMsg())
                .caption_exists(StringUtils.hasText(answer.getCaption()))
                .music(music)
                .build();  // PlaySongInfoDTO 객체 생성 후 리턴
    }

    public void addCaptionService(final String captionText, final Long answerId) {
        Optional<AnswerEntity> entity = repository.findById(answerId);  // 아이디로 entity 찾기
        AnswerEntity answer = entity.get();

        answer.updateCaption(captionText);  // entity에 캡션 수정

        repository.save(answer);
    }
}
