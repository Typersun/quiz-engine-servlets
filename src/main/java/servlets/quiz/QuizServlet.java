package servlets.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.QuizDto;
import exceptions.NotFoundException;
import exceptions.QuizEngineException;
import models.User;
import services.QuizService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private QuizService quizService;
    @Override
    public void init() throws ServletException {
        objectMapper = DIContainer.instance.getObjectMapper();
        quizService = DIContainer.instance.getQuizService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = objectMapper.writeValueAsString(quizService.findAll());
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuizDto quizDto = objectMapper.readValue(request.getReader(), QuizDto.class);
        User user = (User) request.getSession().getAttribute("user");
        quizDto.setUsername(user.getUsername());
        quizService.save(quizDto);
    }
}
