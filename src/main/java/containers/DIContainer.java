package containers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import repositories.QuestionRepository;
import repositories.QuizRepository;
import repositories.UserRepository;
import repositories.impl.QuestionRepositoryImpl;
import repositories.impl.QuizRepositoryImpl;
import repositories.impl.UserRepositoryImpl;
import services.QuestionService;
import services.QuizService;
import services.UserSecurityService;
import services.UserService;
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

    private DIContainer() {}
}
