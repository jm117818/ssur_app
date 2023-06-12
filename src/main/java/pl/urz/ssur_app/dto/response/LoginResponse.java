package pl.urz.ssur_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private TokenResponse tokenResponse;
    private Integer userID;
    private String email;
    private List<String> groups;

}
