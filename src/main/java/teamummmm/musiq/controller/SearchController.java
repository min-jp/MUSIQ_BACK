package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamummmm.musiq.dto.ErrorDTO;
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

            return ResponseEntity.ok().body(dtos);  // List<SearchResultDTO> 리턴
        }
        catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }

    @PostMapping
    public ResponseEntity<?> addSong(
            @RequestParam(value = "question_id") Long questionId,
            @RequestParam(value = "music_id") String musicId
    ) {
        try {
            SearchAddSongDTO dto = service.addSongService(questionId, musicId);  // SearchAddSongDTO 생성

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);  // SearchAddSongDTO 리턴 (201 CREATED)
        }
        catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .error(e.getMessage())
                    .build();

            System.out.println("\nError: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorDTO);
        }
    }
}
