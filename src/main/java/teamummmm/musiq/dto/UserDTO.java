package teamummmm.musiq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamummmm.musiq.model.UserEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long userId;  // 오브젝트 아이디

    private String userName; // 사용자 이름

    private int consecutiveDates;  // 연속으로 대답한 날

    public UserDTO(final UserEntity entity) {
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.consecutiveDates = entity.getConsecutiveDates();
    }

    public static UserEntity toEntity(final UserDTO dto) {
        return UserEntity.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .consecutiveDates(dto.getConsecutiveDates())
                .build();
    }
}
