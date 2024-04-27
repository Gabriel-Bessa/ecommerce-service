package br.com.ecommerce.ecommerceservice.config;

import br.com.ecommerce.ecommerceservice.config.filters.AuthenticationFilter;
import br.com.ecommerce.ecommerceservice.config.filters.AuthorizationHandler;
import br.com.ecommerce.ecommerceservice.config.handlers.AccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final PropertiesConfig propertiesConfig;
    private final BCryptPasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.addFilter(new AuthenticationFilter(authenticationManagerBean(), propertiesConfig));
        http.addFilterBefore(new AuthorizationHandler(), AuthorizationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
