package pl.urz.ssur_app.dto.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
  @Serial
  private static final long serialVersionUID = -368631956323460923L;
  
  private String username;
  private String password;
}
