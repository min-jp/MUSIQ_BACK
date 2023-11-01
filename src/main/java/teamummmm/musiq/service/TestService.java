package teamummmm.musiq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.model.UserQuestionEntity;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestService {
    private UserQuestionRepository repository;

    public List<UserQuestionEntity> test() {
        Long a = 2L;
        List<UserQuestionEntity> b = new ArrayList<>();
        b.add(repository.findById(a).get());
        return b;
    }
}
