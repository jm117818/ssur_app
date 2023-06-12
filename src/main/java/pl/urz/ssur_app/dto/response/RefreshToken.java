package pl.urz.ssur_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
  @Serial
  private static final long serialVersionUID = 1319308104338793402L;

  private Integer userID;
  private Timestamp creationTime;
  private Timestamp expirationTime;
  private Timestamp lastUseTime;
}
