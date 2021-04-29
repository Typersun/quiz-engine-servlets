package servlets.questions;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.QuestionDto;
import exceptions.NotFoundException;
import services.QuestionService;
import services.QuizService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private QuestionService questionService;

    @Override
    public void init() throws ServletException {
        objectMapper = DIContainer.instance.getObjectMapper();
        questionService = DIContainer.instance.getQuestionService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = objectMapper.writeValueAsString(questionService.findAll());
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionDto questionDto = objectMapper.readValue(request.getReader(), QuestionDto.class);
        questionService.save(questionDto);
    }


}



