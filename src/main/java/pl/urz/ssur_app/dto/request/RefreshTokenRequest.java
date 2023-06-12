package pl.urz.ssur_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest implements Serializable {
  @Serial
  private static final long serialVersionUID = 1482673014545436264L;
  
  private String refreshToken;
}
