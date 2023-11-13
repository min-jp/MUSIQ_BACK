package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.CalendarDataDTO;
import teamummmm.musiq.dto.CalendarDateDTO;
import teamummmm.musiq.dto.ErrorDTO;
import teamummmm.musiq.service.CalendarService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("calendar")
public class CalendarController {
    private final CalendarService service;

    @GetMapping("/calendar-data")
    public ResponseEntity<?> dataController(
            @RequestParam(value = "user_id") Long userId
    ) {
        try {
             CalendarDataDTO dto = service.calendarDataService(userId); // CalendarDataDTO 생성

            return ResponseEntity.ok().body(dto);  // CalendarDataDTO 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> dateController(
            @RequestParam(value = "selected_date") LocalDate selectedDate
            ) {
        try {
            List<CalendarDateDTO> dtos = service.dateService(selectedDate); // List<CalendarDateDTO> 생성

            return ResponseEntity.ok().body(dtos);  // List<CalendarDateDTO> 리턴
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
