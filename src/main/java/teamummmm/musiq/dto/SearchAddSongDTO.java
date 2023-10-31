package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchAddSongDTO {
    private String main_color;  // 그 질문의 바뀐 메인 컬러
}
