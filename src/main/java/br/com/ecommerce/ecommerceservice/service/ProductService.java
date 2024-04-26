package br.com.ecommerce.ecommerceservice.service;

import br.com.ecommerce.ecommerceservice.config.AsyncRedisUpdate;
import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import br.com.ecommerce.ecommerceservice.domain.Product;
import br.com.ecommerce.ecommerceservice.domain.Product_;
import br.com.ecommerce.ecommerceservice.domain.dto.ProductDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.ProductSimpleDTO;
import br.com.ecommerce.ecommerceservice.repository.ProductRepository;
import br.com.ecommerce.ecommerceservice.repository.specs.BaseSpecs;
import br.com.ecommerce.ecommerceservice.service.mapper.ProductMapper;
import br.com.ecommerce.ecommerceservice.service.utils.LoadFileUtils;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements BaseSpecs<Product> {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final AsyncRedisUpdate asyncRedisUpdate;

    @Transactional
    public void createProduct(ProductSimpleDTO product, MultipartFile file) {
        product.setImg_url(uploadFileTo(file));
        Product entity = mapper.toEntity(product);
        entity.setId(UUID.randomUUID().toString());
        repository.save(entity);
        // FIXME: Add redis and update method
        asyncRedisUpdate.updateRedisCache();
    }

    private String uploadFileTo(MultipartFile file) {
        if(!LoadFileUtils.isImage(file)) {
            throw new BusinessException("product.error", "product.upload.error");
        }
        //TODO: Upload to S3
        return "img_url";
    }

    public Page<ProductDTO> filterProduct(ProductSimpleDTO filter, Pageable pageable) {
        filter = Optional.ofNullable(filter).orElseGet(ProductSimpleDTO::new);
        Specification<Product> specification = buildSpecAnd(byEquals(Product_.type, filter.getType()));
        specification = buildSpecAnd(specification, porLike(Product_.description, filter.getDescription()));
        specification = buildSpecAnd(specification, byEquals(Product_.value, filter.getValue()));
        return repository.findAll(specification, pageable).map(mapper::toDto);
    }
}
