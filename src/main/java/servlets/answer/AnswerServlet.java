package servlets.answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.AnswerDto;
import exceptions.NotFoundException;
import models.User;
import services.AnswerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private AnswerService answerService;

    @Override
    public void init() throws ServletException {
        objectMapper = DIContainer.instance.getObjectMapper();
        answerService = DIContainer.instance.getAnswerService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = objectMapper.writeValueAsString(answerService.findAll());
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AnswerDto answerDto = objectMapper.readValue(request.getReader(), AnswerDto.class);
        User user = (User) request.getSession().getAttribute("user");
        answerDto.setUsername(user.getUsername());
        answerService.save(answerDto);
    }


}
