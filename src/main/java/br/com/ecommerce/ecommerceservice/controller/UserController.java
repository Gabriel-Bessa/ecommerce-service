package br.com.ecommerce.ecommerceservice.controller;

import br.com.ecommerce.ecommerceservice.domain.dto.UserDetailsDTO;
import br.com.ecommerce.ecommerceservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserService service;

    @GetMapping("/me")
    public UserDetailsDTO me() {
        log.info("[UserController::me] Rest request to get logged user info");
        return service.me();
    }
}
