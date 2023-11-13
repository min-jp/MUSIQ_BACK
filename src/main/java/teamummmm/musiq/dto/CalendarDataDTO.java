package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarDataDTO {
    private Long consecutive_dates;  // 연속 대답 일수

    private List<SelectedDate> selected_dates;  // 날짜별 정보

    @Builder
    @Data
    public static class SelectedDate {
        private LocalDate selected_date;  // 날짜
        private Integer day_color;  // 날짜의 색깔
    }
}
