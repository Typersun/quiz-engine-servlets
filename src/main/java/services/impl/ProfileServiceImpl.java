package services.impl;

import dto.AvatarUploadDto;
import repositories.UserRepository;
import services.ProfileService;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    long maxSizeInBytes = 5242880;
    String path = "C:" + File.separator + "Projects" + File.separator + "Lessons" + File.separator +
            "QuizEngineServlets" + File.separator + "src" + File.separator + "main" + File.separator +
            "resources" + File.separator + "avatars";

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveToDB(AvatarUploadDto avatarUploadDto) throws Exception {

        Part part = avatarUploadDto.getPart();
        part = validatePart(part);
        String uuid = saveFileAndGetUUID(part);
        if (userRepository.isUserAlreadyHaveAvatar(avatarUploadDto.getUserId()).isPresent()) {
            String existingUUID = userRepository.isUserAlreadyHaveAvatar(avatarUploadDto.getUserId()).get();
            Files.deleteIfExists(Paths.get(path + File.separator + existingUUID));
        }

        userRepository.saveAvatarByUserId(uuid, avatarUploadDto.getUserId());
    }

    @Override
    public Part validatePart(Part part) throws Exception {
        if (part.getContentType() != null && part.getContentType().toLowerCase().startsWith("image/")
                && part.getSize() < maxSizeInBytes && part.getSize() > 0) {
            return part;
        } else throw new Exception("Невалидный файл");
    }

    @Override
    public String saveFileAndGetUUID(Part part) throws IOException {
        String[] nameAndExtension = part.getSubmittedFileName().split("\\.");
        String extension = nameAndExtension[1];
        String uuid = generateUUID(extension);
        Files.copy(part.getInputStream(), Paths.get(path + File.separator + uuid + "." + extension));
        uuid = uuid + "." + extension;
        return uuid;
    }

    @Override
    public String generateUUID(String extension) {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (userRepository.isFileExist(uuid + "." + extension).isPresent());
        return uuid;
    }

    @Override
    public File getFileByUUID(String uuid) {
        return new File(path + File.separator + uuid);
    }
}
