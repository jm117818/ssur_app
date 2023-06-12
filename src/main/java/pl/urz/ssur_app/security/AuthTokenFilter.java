package pl.urz.ssur_app.security;

import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.urz.ssur_app.dto.response.ErrorResponse;

import java.io.IOException;
import java.security.SignatureException;

@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private pl.urz.ssur_app.security.UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String bearerToken = request.getHeader("Authorization");

        try {
            this.checkAccessTokenIsNotEmpty(bearerToken);
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(JwtUtils.getSubjectFromAccessToken(bearerToken));
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (final JwtException e) {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(this.createErrorJsonResponseBody(HttpStatus.UNAUTHORIZED, e.getMessage()));
            return;
        } catch (final Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(this.createErrorJsonResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String createErrorJsonResponseBody(final HttpStatus code, final String message) {
        return new Gson().toJson(
                ErrorResponse.builder()
                        .code(code.value())
                        .message(message)
                        .build()
        );
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/public/");
    }

    private void checkAccessTokenIsNotEmpty(String token) {
        if (ObjectUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            throw new JwtException("Bearer token not found");
        }
    }

    @Autowired
    public void setUserDetailsService(final pl.urz.ssur_app.security.UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}