package servlets.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import exceptions.NotFoundException;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {
        userService = DIContainer.instance.getUserService();
        objectMapper = DIContainer.instance.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = objectMapper.writeValueAsString(userService.findAll());
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
