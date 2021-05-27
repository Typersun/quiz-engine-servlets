package filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import containers.DIContainer;
import dto.MessageDto;
import lombok.extern.java.Log;
import models.User;
import repositories.UserRepository;
import utils.JwtHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log
@WebFilter("/*")
public class JwtFilter implements Filter {

    private JwtHelper jwtHelper;
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jwtHelper = DIContainer.instance.getJwtHelper();
        userRepository = DIContainer.instance.getUserRepository();
        objectMapper = DIContainer.instance.getObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getRequestURI().equals("/user/registration") || httpServletRequest.getRequestURI().equals("/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtHelper.getTokenFromRequest(httpServletRequest);
        if (token != null && jwtHelper.validateToken(token)) {
            String email = jwtHelper.getEmailFromToken(token);
            String password = jwtHelper.getPasswordFromToken(token);
            Optional<User> optionalUser = userRepository.findOneByEmail(email);
            if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
                httpServletRequest.getSession().setAttribute("user", optionalUser.get());
                filterChain.doFilter(httpServletRequest, response);
                return;
            }
        }
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        String json = objectMapper.writeValueAsString(new MessageDto("Forbidden"));
        response.getWriter().print(json);
    }

    @Override
    public void destroy() {}
}
