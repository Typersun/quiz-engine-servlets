package servlets.answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.AnswerDto;
import exceptions.NotFoundException;
import models.Answer;
import models.User;
import services.AnswerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/answer/*")
public class OneAnswerServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private AnswerService answerService;

    @Override
    public void init() throws ServletException {
        objectMapper = DIContainer.instance.getObjectMapper();
        answerService = DIContainer.instance.getAnswerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        try {
            AnswerDto answerDto = answerService.findById(id);
            String json = objectMapper.writeValueAsString(answerDto);
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AnswerDto answerDto = objectMapper.readValue(request.getReader(), AnswerDto.class);
        answerDto.setId(Integer.parseInt(request.getPathInfo().substring(1)));
        User user = (User) request.getSession().getAttribute("user");
        answerDto.setUsername(user.getUsername());
        answerService.update(answerDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        answerService.deleteById(id);
    }
}
