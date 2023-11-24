package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CodeLoginDTO {
    private Long user_id;  // 유저 아이디

    private String token;  // 엑세스 토큰
}
