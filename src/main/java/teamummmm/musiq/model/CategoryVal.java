package teamummmm.musiq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor  // 매개변수 없는 생성자
@Getter
public enum CategoryVal {
    DEFAULT_QUESTION,
    MEMORY_QUESTION,
    FUTURE_QUESTION,
    LEISURE_QUESTION,
    WEATHER_QUESTION
}
