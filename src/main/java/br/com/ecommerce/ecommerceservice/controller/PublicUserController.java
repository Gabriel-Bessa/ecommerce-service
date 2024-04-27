package br.com.ecommerce.ecommerceservice.controller;

import br.com.ecommerce.ecommerceservice.domain.dto.UserDTO;
import br.com.ecommerce.ecommerceservice.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/users")
public class PublicUserController {

    private final UserService service;

    @PostMapping
    public void createUser(@RequestBody @Valid UserDTO user) {
        log.info("[ProductController::createProduct] Rest request to create product");
        service.createUser(user);
    }

}
