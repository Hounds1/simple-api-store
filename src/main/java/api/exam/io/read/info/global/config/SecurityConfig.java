package api.exam.io.read.info.global.config;

import api.exam.io.read.info.global.jwt.JwtAccessDeniedHandler;
import api.exam.io.read.info.global.jwt.JwtAuthenticationEnrtyPoint;
import api.exam.io.read.info.global.jwt.TokenProvider;
import api.exam.io.read.info.global.jwt.util.JwtSecurityConfig;
import api.exam.io.read.info.global.security.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.web.cors.CorsConfiguration.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEnrtyPoint jwtAuthenticationEnrtyPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final TokenProvider tokenProvider;

    private static final String PUBLIC = "/api/v1/public/**";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/js/**", "/favicon.ico/**", "/css/**", "/index", "/docs/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
        security.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEnrtyPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authenticationProvider(customAuthenticationProvider())
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(PUBLIC).permitAll()
                .anyRequest().authenticated();

        security
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security
                .apply(new JwtSecurityConfig(tokenProvider));

        security
                .formLogin().disable();

        return security.build();
    }


    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern(ALL);
        configuration.addAllowedHeader(ALL);
        configuration.addAllowedMethod(ALL);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
