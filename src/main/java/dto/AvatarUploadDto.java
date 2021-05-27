package dto;

import lombok.*;

import javax.servlet.http.Part;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarUploadDto {
    private int userId;
    private Part part;
}
