package br.com.ecommerce.ecommerceservice.controller;

import br.com.ecommerce.ecommerceservice.domain.dto.ProductDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.ProductSimpleDTO;
import br.com.ecommerce.ecommerceservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/product")
public class PublicProductController {

    private final ProductService service;

    @PostMapping("/filter")
    public Page<ProductDTO> filterProduct(@RequestBody ProductSimpleDTO filter, Pageable pageable) {
        log.info("[ProductController::filterProduct] Rest request to create product");
        return service.filterProduct(filter, pageable);
    }
}
