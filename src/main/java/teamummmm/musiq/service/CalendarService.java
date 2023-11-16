package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;
import teamummmm.musiq.dto.CalendarDataDTO;
import teamummmm.musiq.dto.CalendarDateDTO;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.ColorVal;
import teamummmm.musiq.model.MusicInfoEntity;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.spotify.SpotifyService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class CalendarService {
    private final AnswerRepository repository;
    private final SpotifyService spotifyService;

    public CalendarDataDTO calendarDataService(final Long userId) {
        List<AnswerEntity> entities = repository.findByUserQuestion_User_UserId(userId);  // List<AnswerEntity> 찾기

        Map<LocalDate, List<AnswerEntity>> groupByDate = entities.stream()
                .collect(Collectors.groupingBy(AnswerEntity::getAnswerDate));  // 날짜별로 그룹핑 후 Map 객체 생성

        // 연속 일 수 찾기
        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")); // 어제 날짜로 설정
        Long consecutiveDates = 0L;  // 연속 날짜
        if (groupByDate.containsKey(currentDate)) {  // 오늘 대답을 했으면 추가
            consecutiveDates++;
        }
        currentDate = currentDate.minusDays(1); // 이전 날짜로 이동

        while (groupByDate.containsKey(currentDate)) {  // 연속된 날짜 탐색
            consecutiveDates++;
            currentDate = currentDate.minusDays(1); // 이전 날짜로 이동
        }


        List<CalendarDataDTO.SelectedDate> selectedDates = groupByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    // 날짜별 색상
                    ColorVal dayColor = entry.getValue().stream()
                            .map(answer -> answer.getMusicInfo().getMusicColor())
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse(null);

                    return CalendarDataDTO.SelectedDate.builder()
                            .selected_date(entry.getKey())  // 날짜
                            .day_color(dayColor.ordinal())  // 날짜별 색상
                            .build();
                }).toList();  // List<CalendarDataDTO.SelectedDate> 객체 생성

        return CalendarDataDTO.builder()
                .consecutive_dates(consecutiveDates)
                .selected_dates(selectedDates)
                .build();  // CalendarDataDTO 생성 후 리턴
    }

    public List<CalendarDateDTO> dateService(final LocalDate selectedDate, final Long userId) {
        List<AnswerEntity> entities = repository.findByAnswerDateAndUserQuestion_User_UserId(selectedDate, userId);

        Map<ColorVal, List<AnswerEntity>> groupByColor = entities.stream()
                .collect(Collectors.groupingBy(item -> item.getMusicInfo().getMusicColor()));

        return groupByColor.entrySet().stream()
                .map(entry -> {
                    List<CalendarDateDTO.Music> musicList = new ArrayList<>();  // music
                    entry.getValue()
                            .forEach(answer -> {
                                MusicInfoEntity musicInfo = answer.getMusicInfo();  // MusicInfoEntity

                                Track track = spotifyService.getTrack(musicInfo.getMusicId());  // 트랙 가져오기

                                ArtistSimplified artist = track.getArtists()[0];  // 아티스트
                                Image image = track.getAlbum().getImages()[0];  // 커버 이미지

                                musicList.add(CalendarDateDTO.Music.builder()
                                        .music_id(musicInfo.getMusicId())
                                        .music_name(track.getName())
                                        .artist_name(artist.getName())
                                        .cover_url(image.getUrl())
                                        .build());  // Music 객체 생성 후 musicList에 추가
                            });

                    return CalendarDateDTO.builder()
                            .Color(entry.getKey().ordinal())  // 색깔들
                            .musics(musicList)  // 음악들
                            .build();
                }).toList();
    }
}
