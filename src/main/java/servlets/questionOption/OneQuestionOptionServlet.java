package servlets.questionOption;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.QuestionOptionDto;
import exceptions.NotFoundException;
import services.QuestionOptionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question-option/*")
public class OneQuestionOptionServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private QuestionOptionService questionOptionService;

    @Override
    public void init() throws ServletException {
        objectMapper = DIContainer.instance.getObjectMapper();
        questionOptionService = DIContainer.instance.getQuestionOptionService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        try {
            QuestionOptionDto questionOptionDto = questionOptionService.findById(id);
            String json = objectMapper.writeValueAsString(questionOptionDto);
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionOptionDto questionOptionDto = objectMapper.readValue(request.getReader(), QuestionOptionDto.class);
        questionOptionDto.setId(Integer.parseInt(request.getPathInfo().substring(1)));
        questionOptionService.update(questionOptionDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        questionOptionService.deleteById(id);
    }


}
