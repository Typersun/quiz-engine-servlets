package services;

import dto.AvatarUploadDto;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public interface ProfileService {
    public void saveToDB(AvatarUploadDto avatarUploadDto) throws Exception;
    public Part validatePart(Part part) throws Exception;
    public String saveFileAndGetUUID(Part part) throws IOException;
    public String generateUUID(String extension);
    public File getFileByUUID(String uuid);
}
