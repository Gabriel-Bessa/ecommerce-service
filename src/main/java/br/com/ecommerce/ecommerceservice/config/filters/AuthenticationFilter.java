package br.com.ecommerce.ecommerceservice.config.filters;

import br.com.ecommerce.ecommerceservice.config.PropertiesConfig;
import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import br.com.ecommerce.ecommerceservice.domain.dto.UserDetailsDTO;
import br.com.ecommerce.ecommerceservice.service.NoSqlService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final NoSqlService noSqlService;
    private final PropertiesConfig config;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null) {
            throw new BusinessException("product.error", "product.upload.error");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        Date expiration = new Date(System.currentTimeMillis() + config.getTime());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsDTO user = (UserDetailsDTO) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(config.getSecret().getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiration)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("expiration", String.valueOf(expiration));
        setInRedis(accessToken, user);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.info("Successful login to : {}", user.getUsername());
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private void setInRedis(String accessToken, UserDetailsDTO user) throws JsonProcessingException {
        log.info("Setting token: {} in REDIS", accessToken);
        Map<String, Object> obj = new HashMap<>();
        obj.put("id", user.getId());
        obj.put("email", user.getUsername());
        obj.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        noSqlService.setValue(accessToken, new ObjectMapper().writeValueAsString(obj), TimeUnit.DAYS, 7L);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(204);
    }
}
