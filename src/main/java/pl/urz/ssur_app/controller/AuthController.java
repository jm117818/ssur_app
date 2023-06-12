package pl.urz.ssur_app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.urz.ssur_app.dto.request.LoginRequest;
import pl.urz.ssur_app.dto.request.RegisterRequest;
import pl.urz.ssur_app.dto.response.LoginResponse;
import pl.urz.ssur_app.dto.request.RefreshTokenRequest;
import pl.urz.ssur_app.dto.response.TokenResponse;
import pl.urz.ssur_app.service.AuthService;

@CrossOrigin(maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api")
public class AuthController {
  AuthService authService;

  @PostMapping(value = "/public/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginResponse> authenticateUser(@RequestBody final LoginRequest loginRequest) {
    return this.authService.signIn(loginRequest);
  }

  @PostMapping(value = "/public/auth/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenResponse> refreshToken(@RequestBody final RefreshTokenRequest request) {
    return this.authService.refreshToken(request);
  }

  @PostMapping(value = "/public/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> registerUser(@RequestBody final RegisterRequest registerRequest){
    return this.authService.signUp(registerRequest);
  }
  @Autowired
  public void setAuthService(final AuthService authService) {
    this.authService = authService;
  }

}