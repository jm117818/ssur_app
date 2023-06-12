package pl.urz.ssur_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pl.urz.ssur_app.dto.request.LoginRequest;
import pl.urz.ssur_app.dto.request.RegisterRequest;
import pl.urz.ssur_app.dto.response.LoginResponse;
import pl.urz.ssur_app.dto.request.RefreshTokenRequest;
import pl.urz.ssur_app.dto.response.TokenResponse;
import pl.urz.ssur_app.model.User;
import pl.urz.ssur_app.repository.UsersRepository;
import pl.urz.ssur_app.security.JwtUtils;
import pl.urz.ssur_app.security.UserDetailsImpl;

import java.util.List;

@Slf4j
@Service
public class AuthService {
  AuthenticationManager authenticationManager;
  RefreshTokenService refreshTokenService;
  UsersRepository usersRepository;
  PasswordEncoder passwordEncoder;


  public ResponseEntity<LoginResponse> signIn(final LoginRequest loginRequest) {
    final Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername().trim(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    final String jwtToken = JwtUtils.generateAccessToken(userPrincipal);
    final String refreshToken = this.refreshTokenService.generateRefreshToken(userPrincipal.getUserID());

    final List<String> roles = userPrincipal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

    return ResponseEntity.ok(new LoginResponse(
            TokenResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build(),
            userPrincipal.getUserID(),
            userPrincipal.getUsername(),
            roles)
    );
  }

  public ResponseEntity<Void> signUp(final RegisterRequest registerRequest){
    User user = new User(null,registerRequest.getFirstName(),registerRequest.getLastName(),registerRequest.getEmail(),passwordEncoder.encode(registerRequest.getPassword()),null);

    usersRepository.insert(user);
    return ResponseEntity.ok().build();
  }

  public ResponseEntity<TokenResponse> refreshToken(final RefreshTokenRequest request) {
    this.validateRefreshTokenRequest(request);
    JwtUtils.validateRefreshToken(request.getRefreshToken());
    return this.refreshTokenService.refreshToken(request);
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private void validateRefreshTokenRequest(final RefreshTokenRequest request) {
    if (ObjectUtils.isEmpty(request.getRefreshToken())) {
      throw new IllegalArgumentException("Token in request cannot be empty");
    }
  }

  @Autowired
  public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Autowired
  public void setRefreshTokenService(final RefreshTokenService refreshTokenService) {
    this.refreshTokenService = refreshTokenService;
  }

  @Autowired
  public void setUsersRepository(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }
}
