package pl.urz.ssur_app.security;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pl.urz.ssur_app.dto.response.ErrorResponse;


import java.io.IOException;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        //if not permission 403
        //if not found 404
        log.warn("{} {} {} Authentication exception: {} with message {}", response.getStatus(), request.getMethod(), request.getRequestURI(),
                authException.getClass().getSimpleName(), authException.getMessage());

        response.setContentType("application/json");
        if (HttpStatus.NOT_FOUND.value() == response.getStatus()) {
            this.setNotFoundResponse(response);
            return;
        }
        this.setForbiddenResponse(response, authException.getMessage());
    }

    private void setNotFoundResponse(final HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.getWriter().write(new Gson().toJson(
                ErrorResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .build()
        ));
    }

    private void setForbiddenResponse(final HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(new Gson().toJson(
                ErrorResponse.builder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .message(message)
                        .build()
        ));
    }
}