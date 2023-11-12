package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.model.AnswerEntity;
import teamummmm.musiq.repository.AnswerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class PlaySongService {
    private final AnswerRepository repository;

    public void addCaptionService(final String captionText, final Long answerId) {
        Optional<AnswerEntity> entity = repository.findById(answerId);  // 아이디로 entity 찾기

        AnswerEntity answer = entity.get();

        answer.updateCaption(captionText);  // entity에 캡션 수정

        repository.save(answer);
    }
}
