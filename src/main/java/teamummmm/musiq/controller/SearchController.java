package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamummmm.musiq.dto.ResponseDTO;
import teamummmm.musiq.dto.SearchAddSongDTO;
import teamummmm.musiq.dto.SearchResultDTO;
import teamummmm.musiq.service.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("search")
public class SearchController {
    private final SearchService service;  // service

    @GetMapping
    public ResponseEntity<?> searchResult(
            @RequestParam(value = "search_text") String searchText
    ) {
        try {
            List<SearchResultDTO> dtos = service.searchService(searchText);  // List<SearchResultDTO> 생성

            ResponseDTO<SearchResultDTO> response = ResponseDTO.<SearchResultDTO>builder()
                    .data(dtos)
                    .build();  // List<SearchResultDTO> 이용하여 ResponseDTO 초기화

            return ResponseEntity.ok().body(response);  // ResponseDTO 리턴
        }
        catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<SearchResultDTO> response = ResponseDTO.<SearchResultDTO>builder()
                    .error(error)
                    .build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> addSong(
            @RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "question_id") Long questionId,
            @RequestParam(value = "music_id") String musicId
    ) {
        try {
            SearchAddSongDTO dto = service.addSongService(userId, questionId, musicId);  // SearchAddSongDTO 생성

            ResponseDTO<SearchAddSongDTO> response = ResponseDTO.<SearchAddSongDTO>builder()
                    .data(List.of(dto))
                    .build();  // SearchAddSongDTO 이용하여 ResponseDTO 초기화

            return ResponseEntity.status(HttpStatus.CREATED).body(response);  // ResponseDTO 리턴 (201 CREATED)
        }
        catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<SearchAddSongDTO> response = ResponseDTO.<SearchAddSongDTO>builder()
                    .error(error)
                    .build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
