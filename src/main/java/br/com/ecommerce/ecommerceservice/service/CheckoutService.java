package br.com.ecommerce.ecommerceservice.service;

import br.com.ecommerce.ecommerceservice.config.SecurityUtils;
import br.com.ecommerce.ecommerceservice.domain.CoverageAnalysis;
import br.com.ecommerce.ecommerceservice.domain.Product;
import br.com.ecommerce.ecommerceservice.domain.Purchase;
import br.com.ecommerce.ecommerceservice.domain.PurchaseProduct;
import br.com.ecommerce.ecommerceservice.domain.dto.CheckoutDTO;
import br.com.ecommerce.ecommerceservice.domain.dto.CheckoutProductDTO;
import br.com.ecommerce.ecommerceservice.domain.enuns.CoverageAnalysisStatus;
import br.com.ecommerce.ecommerceservice.domain.enuns.PurchaseStatus;
import br.com.ecommerce.ecommerceservice.repository.ProductRepository;
import br.com.ecommerce.ecommerceservice.repository.PurchaseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckoutService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Transactional
    public void createCheckout(@Valid CheckoutDTO checkout) {
        Purchase purchase = new Purchase();
        purchase.setUser(userService.findByEmail(SecurityUtils.getAuthEmail()));
        purchase.setId(UUID.randomUUID().toString());
        purchase.setPurchaseProducts(new ArrayList<>());
        purchase.setCoverageAnalyses(new ArrayList<>());
        List<Product> products = Lists.newArrayList(productRepository.findAllById(checkout.getProducts().stream().map(CheckoutProductDTO::getId).collect(Collectors.toSet())));
        bindItems(products, purchase, checkout);
        purchaseRepository.save(purchase);
    }

    private static void bindItems(List<Product> products, Purchase purchase, CheckoutDTO checkout) {
        purchase.setStatus(PurchaseStatus.FINALIZED);
        products.forEach(product -> {
            CheckoutProductDTO checkoutProduct = checkout.getProducts().stream().filter(it -> it.getId().equals(product.getId())).findFirst().orElse(new CheckoutProductDTO());
            if (product.getNeedsConfirmation()) {
                purchase.setStatus(PurchaseStatus.PENDING);
                CoverageAnalysis coverageAnalysis = bindCoverageAnalysis(product, checkoutProduct, purchase);
                purchase.getCoverageAnalyses().add(coverageAnalysis);
                return;
            }
            PurchaseProduct purchaseProduct = bindPurchaseProduct(product, checkoutProduct, purchase);
            purchase.getPurchaseProducts().add(purchaseProduct);
        });
    }

    private static CoverageAnalysis bindCoverageAnalysis(Product product, CheckoutProductDTO checkoutProduct, Purchase purchase) {
        CoverageAnalysis coverageAnalysis = new CoverageAnalysis();
        coverageAnalysis.setPurchase(purchase);
        coverageAnalysis.setId(UUID.randomUUID().toString());
        coverageAnalysis.setProduct(product);
        coverageAnalysis.setQuantity(checkoutProduct.getQuantity());
        coverageAnalysis.setStatus(CoverageAnalysisStatus.PENDING);
        return coverageAnalysis;
    }

    private static PurchaseProduct bindPurchaseProduct(Product product, CheckoutProductDTO checkoutProduct, Purchase purchase) {
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        purchaseProduct.setPurchase(purchase);
        purchaseProduct.setId(UUID.randomUUID().toString());
        purchaseProduct.setProduct(product);
        purchaseProduct.setQuantity(checkoutProduct.getQuantity());
        return purchaseProduct;
    }
}
