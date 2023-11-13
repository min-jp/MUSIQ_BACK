package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.CalendarDataDTO;
import teamummmm.musiq.dto.CalendarDateDTO;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.model.ColorVal;
import teamummmm.musiq.repository.AnswerRepository;

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

    public CalendarDataDTO calendarDataService(final Long userId) {
        List<AnswerEntity> entities = repository.findByUserQuestion_User_UserId(userId);  // List<AnswerEntity> 찾기

        Map<LocalDate, List<AnswerEntity>> groupByDate = entities.stream()
                .collect(Collectors.groupingBy(AnswerEntity::getAnswerDate));  // 날짜별로 그룹핑 후 Map 객체 생성

        // 연속 일 수 찾기
        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(3); // 어제 날짜로 설정
        Long count = 0L;

        while (groupByDate.containsKey(currentDate)) {
            count++;
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
                .consecutive_dates(count)
                .selected_dates(selectedDates)
                .build();
    }

    public List<CalendarDateDTO> dateService(final LocalDate selectedDate) {
        return new ArrayList<>();
    }
}
