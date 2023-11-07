package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import teamummmm.musiq.dto.SearchAddSongDTO;
import teamummmm.musiq.dto.SearchResultDTO;
import teamummmm.musiq.repository.UserQuestionRepository;
import teamummmm.musiq.spotify.SpotifySearch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class SearchService {
    private final SpotifySearch spotifySearch;
    private final UserQuestionRepository repository;

    public List<SearchResultDTO> searchService (final String searchText) {  // 곡 검색
        Paging<Track> trackPaging = spotifySearch.searchTracks(searchText);

        validateTrack(trackPaging);  // 검색 결과가 비었는지 검사

        List<SearchResultDTO> dtos = Arrays.stream(trackPaging.getItems())  // stream 이용해서 변환
                .map(item -> {
                    String artistName = Arrays.stream(item.getArtists()).findFirst().get().getName();  // 아티스트 이름

                    Image image = Arrays.stream(item.getAlbum().getImages()).findFirst().get();  // 커버 이미지
                    SearchResultDTO.CoverImage coverImage = SearchResultDTO.CoverImage.builder()
                            .height(image.getHeight())
                            .width(image.getWidth())
                            .url(image.getUrl())
                            .build();

                    SearchResultDTO dto = SearchResultDTO.builder()  // dto 생성
                            .music_name(item.getName())
                            .music_id(item.getId())
                            .artist_name(artistName)
                            .cover_image(coverImage)
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());

        return dtos;
    }

    private void validateTrack(final Paging<Track> trackPaging) {  // 검색 결과 검사
        if (trackPaging == null) {
            throw new RuntimeException("Result is empty");
        }
    }

    public SearchAddSongDTO addSongService (final Long userId, final Long questionId, final String musicId) {

        return new SearchAddSongDTO();
    }
}
