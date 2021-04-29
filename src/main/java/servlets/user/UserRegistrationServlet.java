package servlets.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.RegistrationDto;
import dto.TokenDto;
import exceptions.QuizEngineException;
import services.UserSecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/registration")
public class UserRegistrationServlet extends HttpServlet {
    private UserSecurityService userSecurityService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userSecurityService = DIContainer.instance.getUserSecurityService();
        objectMapper = DIContainer.instance.getObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RegistrationDto registrationDto = objectMapper.readValue(request.getReader(), RegistrationDto.class);
            TokenDto tokenDto = userSecurityService.save(registrationDto);
            String jsonToken = objectMapper.writeValueAsString(tokenDto);
            response.getWriter().print(jsonToken);
        } catch (QuizEngineException e) {
            String json = objectMapper.writeValueAsString(e.getErrorEntity());
            response.setStatus(e.getErrorEntity().getStatus());
            response.getWriter().print(json);
        }
    }


}
