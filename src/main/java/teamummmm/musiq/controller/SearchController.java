package teamummmm.musiq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamummmm.musiq.dto.ResponseDTO;
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
}
