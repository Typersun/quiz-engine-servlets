package servlets.questionOption;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.QuestionOptionDto;
import exceptions.NotFoundException;
import models.QuestionOption;
import services.QuestionOptionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question-option")
public class QuestionOptionServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private QuestionOptionService questionOptionService;

    @Override
    public void init() throws ServletException {
        objectMapper = DIContainer.instance.getObjectMapper();
        questionOptionService = DIContainer.instance.getQuestionOptionService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = objectMapper.writeValueAsString(questionOptionService.findAll());
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionOptionDto questionOptionDto = objectMapper.readValue(request.getReader(), QuestionOptionDto.class);
        questionOptionService.save(questionOptionDto);
    }


}
