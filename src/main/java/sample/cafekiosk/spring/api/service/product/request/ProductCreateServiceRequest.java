package sample.cafekiosk.spring.api.service.product.request;

import lombok.Builder;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private int price;
    private String name;

    @Builder
    public ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus,
        int price,
        String name) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.price = price;
        this.name = name;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
            .productNumber(nextProductNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }

}
