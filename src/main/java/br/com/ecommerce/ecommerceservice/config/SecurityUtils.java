package br.com.ecommerce.ecommerceservice.config;

import br.com.ecommerce.ecommerceservice.domain.dto.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getAuthEmail() {
        return getAuthUser().getUsername();
    }

    public static UserDetailsDTO getAuthUser() {
        return (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}