package br.com.ecommerce.ecommerceservice.controller;

import br.com.ecommerce.ecommerceservice.domain.dto.CheckoutDTO;
import br.com.ecommerce.ecommerceservice.service.CheckoutService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/checkout")
public class CheckoutController {

    private final CheckoutService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void createCheckout(@RequestBody @Valid CheckoutDTO checkout) {
        log.info("[CheckoutController::createCheckout] Rest request to create purchases");
        service.createCheckout(checkout);
    }

}
