package pl.urz.ssur_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -368631956323460923L;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
