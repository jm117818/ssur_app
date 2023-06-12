package pl.urz.ssur_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.urz.ssur_app.security.AuthEntryPointJwt;
import pl.urz.ssur_app.security.AuthTokenFilter;
import pl.urz.ssur_app.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    AuthEntryPointJwt authEntryPointJwt;
    UserDetailsServiceImpl userDetailsService;
    AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(this.authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
//                        .requestMatchers("/test/admin").hasAnyAuthority(RoleEnum.ADMIN.name())
//                        .requestMatchers("/test/user").hasAnyAuthority(RoleEnum.USER.name(), RoleEnum.ADMIN.name())
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(this.authenticationManager(http))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(this.authEntryPointJwt)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(final HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
        return auth.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setAuthEntryPointJwt(final AuthEntryPointJwt authEntryPointJwt) {
        this.authEntryPointJwt = authEntryPointJwt;
    }

    @Autowired
    public void setUserDetailsService(final UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setAuthTokenFilter(final AuthTokenFilter authTokenFilter) {
        this.authTokenFilter = authTokenFilter;
    }
}