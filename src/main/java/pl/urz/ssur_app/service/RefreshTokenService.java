package pl.urz.ssur_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.urz.ssur_app.dto.request.RefreshTokenRequest;
import pl.urz.ssur_app.dto.response.TokenResponse;
import pl.urz.ssur_app.repository.UsersRepository;
import pl.urz.ssur_app.security.JwtUtils;
import pl.urz.ssur_app.security.UserDetailsImpl;

@Slf4j
@Service
public class RefreshTokenService {
  UsersRepository usersRepository;
  public ResponseEntity<TokenResponse> refreshToken(final RefreshTokenRequest request) {
    final String tokenSubject = JwtUtils.getSubjectFromRefreshToken(request.getRefreshToken());
    final UserDetailsImpl userDetails = new UserDetailsImpl(this.usersRepository.findOneById(Integer.valueOf(tokenSubject))
            .orElseThrow(() ->new UsernameNotFoundException(String.format("User %s not found", tokenSubject))));

    final String accessToken = JwtUtils.generateAccessToken(userDetails);
    final TokenResponse response = TokenResponse.builder()
      .accessToken(accessToken)
      .refreshToken(request.getRefreshToken())
      .build();
    return ResponseEntity.ok(response);
  }

  public String generateRefreshToken(final Integer userID) {
    return JwtUtils.generateRefreshToken(userID);
  }

  @Autowired
  public void setUsersRepository(final UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }
}
