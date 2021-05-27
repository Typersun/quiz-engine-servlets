package servlets.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.MessageDto;
import dto.UserDto;
import exceptions.NotFoundException;
import models.User;
import repositories.UserRepository;
import services.UserService;
import services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class OneUserServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userService = DIContainer.instance.getUserService();
        objectMapper = DIContainer.instance.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        try {
            UserDto userDto = userService.findById(id);
            String json = objectMapper.writeValueAsString(userDto);
            response.getWriter().print(json);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDto userDto = objectMapper.readValue(request.getReader(), UserDto.class);
            userDto.setId(Integer.parseInt(request.getPathInfo().substring(1)));
            User user = (User) request.getSession().getAttribute("user");
            if (userDto.getId() != user.getId()) {
                String json = objectMapper.writeValueAsString(new MessageDto("Forbidden"));
                response.getWriter().print(json);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            userService.update(userDto);
        } catch (Exception e) {
            String json = objectMapper.writeValueAsString(new MessageDto("Wrong request"));
            response.getWriter().print(json);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            String json = objectMapper.writeValueAsString(new MessageDto("Wrong request"));
            response.getWriter().print(json);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }


}
