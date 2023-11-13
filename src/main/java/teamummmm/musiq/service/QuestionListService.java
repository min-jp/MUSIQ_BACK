package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;
import teamummmm.musiq.dto.QuestionAnswerDTO;
import teamummmm.musiq.dto.QuestionListDTO;
import teamummmm.musiq.model.*;
import teamummmm.musiq.repository.AnswerRepository;
import teamummmm.musiq.repository.UserQuestionRepository;
import teamummmm.musiq.spotify.SpotifyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionListService {
    private final UserQuestionRepository userQuestionRepository;
    private final AnswerRepository answerRepository;
    private final SpotifyService spotifyService;

    public List<QuestionListDTO> questionListService(final Long userId) {
        List<UserQuestionEntity> entities =  userQuestionRepository.findByUser_UserIdAndAnswerPageAnsListIsNotEmpty(userId);

        return entities.stream()
                .map(entity -> {
                    CommonQuestionEntity commonQuestion = entity.getCommonQuestion();  // 공통 질문 내용

                    return QuestionListDTO.builder()
                            .question_id(entity.getUserQuestionId())
                            .question_message(commonQuestion.getQuestionMsg())
                            .emoji(commonQuestion.getEmoji())
                            .main_color(answerRepository.findBestColor(entity.getUserQuestionId()).ordinal())  // best color 찾기
                            .build();
                })
                .toList();
    }

    public QuestionAnswerDTO answerListService(final Long questionId) {
        return QuestionAnswerDTO.builder()
                .color_counts(colorCountCal(questionId))
                .answer_dates(groupByDate(questionId))
                .build();  // QuestionAnswerDTO 생성 후 리턴
    }

    private List<QuestionAnswerDTO.ColorCount> colorCountCal(final Long questionId) {  // 컬러 비율 계산
        List<Object[]> objects = answerRepository.countMusicColors(questionId);

        return objects.stream().map(object -> QuestionAnswerDTO.ColorCount.builder()
                .color_name(((ColorVal) object[0]).ordinal())  // 컬러 이름
                .count((Long) object[1])  // count
                .build())
                .toList();
    }

    private List<QuestionAnswerDTO.AnswerDate> groupByDate(final Long questionId) {  // 날짜별로 데이터 묶어서 처리
        List<AnswerEntity> entities = answerRepository.findByUserQuestion_UserQuestionId(questionId);

        Map<LocalDate, List<AnswerEntity>> groupedByDate = entities.stream()
                .collect(Collectors.groupingBy(AnswerEntity::getAnswerDate));  // 날짜별로 그룹핑 후 Map 객체 생성

        return groupedByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 날짜 오래된 순으로 정렬
                .map(entry -> {
                    List<QuestionAnswerDTO.AnswerDate.Answer> answers = entry.getValue().stream()
                            .map(answer -> {
                                MusicInfoEntity musicInfo = answer.getMusicInfo();  // MusicInfoEntity

                                Track track = spotifyService.getTrack(musicInfo.getMusicId()); // 트랙 가져오기

                                ArtistSimplified artist = track.getArtists()[0];  // 아티스트
                                Image image = track.getAlbum().getImages()[0];  // 커버 이미지

                                QuestionAnswerDTO.AnswerDate.Answer.Music music = QuestionAnswerDTO.AnswerDate.Answer.Music.builder()
                                        .music_id(musicInfo.getMusicId())
                                        .music_color(musicInfo.getMusicColor().ordinal())
                                        .music_name(track.getName())
                                        .artist_name(artist.getName())
                                        .cover_url(image.getUrl())
                                        .build();  // Music 객체 생성

                                return QuestionAnswerDTO.AnswerDate.Answer.builder()
                                        .answer_id(answer.getAnswerId())
                                        .caption(answer.getCaption())
                                        .music(music)  // Music 객체 삽입
                                        .build();  // Answer 객체 생성 후 리턴
                            })
                            .collect(Collectors.toList());  // Answer 객체 생성

                    // 날짜별 색상
                    ColorVal dayColor = entry.getValue().stream()
                            .map(answer -> answer.getMusicInfo().getMusicColor())
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse(null);

                    return QuestionAnswerDTO.AnswerDate.builder()
                            .answer_date(entry.getKey())
                            .day_color(dayColor.ordinal())
                            .answers(answers)
                            .build();
                })
                .toList();
    }
}
