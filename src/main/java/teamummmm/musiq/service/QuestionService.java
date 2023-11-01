package teamummmm.musiq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamummmm.musiq.dto.RequestQuestionDTO;
import teamummmm.musiq.repository.CommonQuestionRepository;
import teamummmm.musiq.repository.UserQuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor  // 생성자 주입
public class QuestionService {
    private UserQuestionRepository userQuestionRepository;
    private CommonQuestionRepository commonQuestionRepository;

    public List<RequestQuestionDTO> mainQuestionService(Long userId, boolean refresh) {
        // (1) 리프레시 여부 확인
        if (refresh) {  // 일반 질문 호출

        }
        else {  // 새로고침한 질문 호출

        }

        // (2) 유저 아이디 받아서 질문 리턴 (유저질물)


        // (3) 유저질문에서의 id바탕으로 검색?

        // (4) 데이터 적재
        // (5) 리턴
        List<RequestQuestionDTO> list = new ArrayList<>();
        return list;
    }

    // 리프레시 질문

    // 일반 질문
}
