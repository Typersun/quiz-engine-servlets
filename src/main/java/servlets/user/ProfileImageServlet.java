package servlets.user;

import containers.DIContainer;
import dto.AvatarUploadDto;
import models.User;
import services.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/user/profile/avatar/*")
@MultipartConfig
public class ProfileImageServlet extends HttpServlet {
    private ProfileService profileService;
    @Override
    public void init() throws ServletException {
        profileService = DIContainer.instance.getProfileService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getPathInfo().substring(1);
        File file = profileService.getFileByUUID(uuid);
        response.setContentType("image");
        Files.copy(file.toPath(), response.getOutputStream());
        response.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("avatar");
        User user = (User) request.getSession().getAttribute("user");
        AvatarUploadDto avatarUploadDto = AvatarUploadDto.builder()
                .userId(user.getId())
                .part(part)
                .build();
        try {
            profileService.saveToDB(avatarUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
