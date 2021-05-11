package containers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import repositories.*;
import repositories.impl.*;
import services.*;
import services.impl.*;
import utils.JwtHelper;

@Getter
public class DIContainer {
    public static DIContainer instance = new DIContainer();

    private final QuizRepository quizRepository = new QuizRepositoryImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final QuestionRepository questionRepository = new QuestionRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserService userService = new UserServiceImpl(userRepository);
    private final JwtHelper jwtHelper = new JwtHelper();
    private final Validator validator = new Validator(userRepository);
    private final UserSecurityService userSecurityService = new UserSecurityServiceImpl(userRepository, jwtHelper, validator);
    private final QuizService quizService = new QuizServiceImpl(quizRepository, userRepository);
    private final QuestionService questionService = new QuestionServiceImpl(questionRepository, quizRepository);
    private final QuestionOptionRepository questionOptionRepository = new QuestionOptionRepositoryImpl();
    private final QuestionOptionService questionOptionService = new QuestionOptionServiceImpl(questionOptionRepository, questionRepository);
    private final AnswerRepository answerRepository = new AnswerRepositoryImpl();
    private final AnswerService answerService = new AnswerServiceImpl(answerRepository, questionRepository, questionOptionRepository, userRepository);

    private DIContainer() {}
}
