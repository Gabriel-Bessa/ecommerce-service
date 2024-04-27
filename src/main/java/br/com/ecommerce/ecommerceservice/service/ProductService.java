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
import br.com.ecommerce.ecommerceservice.service.utils.Uploader;
import com.amazonaws.services.s3.AmazonS3;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
public class ProductService implements BaseSpecs<Product>, Uploader {

    public static final String BUCKET_URL = "https://bessatech.s3.amazonaws.com/";
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final AsyncRedisUpdate asyncRedisUpdate;
    private final AmazonS3 s3;

    @Transactional
    public void createProduct(ProductSimpleDTO product, MultipartFile file) {
        product.setImgUrl(uploadFileTo(file));
        Product entity = mapper.toEntity(product);
        entity.setId(UUID.randomUUID().toString());
        repository.save(entity);
        asyncRedisUpdate.updateRedisCache();
    }

    private String uploadFileTo(MultipartFile file) {
        if(!LoadFileUtils.isImage(file)) {
            throw new BusinessException("product.error", "product.upload.error");
        }
        return saveImageFileName(s3, "bessatech", file, "products/");
    }

    @Cacheable(cacheNames = "filter_products", key = "#root.method.name + ':' + #filter.type + ':' + #filter.name + ':' + #filter.description + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<ProductDTO> filterProduct(ProductSimpleDTO filter, Pageable pageable) {
        filter = Optional.ofNullable(filter).orElseGet(ProductSimpleDTO::new);
        Specification<Product> specification = buildSpecAnd(byEquals(Product_.type, filter.getType()));
        specification = buildSpecAnd(specification, porLike(Product_.description, filter.getDescription()));
        specification = buildSpecAnd(specification, byEquals(Product_.value, filter.getValue()));
        return repository.findAll(specification, pageable).map(mapper::toDto);
    }
}
