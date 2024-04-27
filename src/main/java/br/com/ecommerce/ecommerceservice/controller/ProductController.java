package br.com.ecommerce.ecommerceservice.controller;

import br.com.ecommerce.ecommerceservice.domain.dto.ProductDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.ProductSimpleDTO;
import br.com.ecommerce.ecommerceservice.service.ProductService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public void createProduct(@RequestPart(name = "file") MultipartFile file, @RequestPart("form") @Valid ProductSimpleDTO product) {
        log.info("[ProductController::createProduct] Rest request to create product");
        service.createProduct(product, file);
    }

    @PostMapping("/filter")
    public Page<ProductDTO> filterProduct(@RequestBody ProductSimpleDTO filter, Pageable pageable) {
        log.info("[ProductController::filterProduct] Rest request to create product");
        return service.filterProduct(filter, pageable);
    }

}
