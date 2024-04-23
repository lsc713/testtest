package sample.cafekiosk.spring.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

/*readOnly = true : 읽기전용
* 중요하게 분리해야
* ->CUD가 안되고 조회만가능. JPA:CUD 스냅샷저장,변경감지를하지않아 (성능 향상)
* CQRS - Command / Query 분리
*
*
* */
@Transactional(readOnly = true)
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    //TODO 동시성이슈 여러명이 상을 등록해버린다면? ->UUID의 활용을 통한 상품번호등록
    @Test
    @DisplayName("신규 상품을 등록한. 상품번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
    public void createProduct() throws Exception {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product1);

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5000)
            .build();
        //when -> 보통한줄 행위를 정의

        ProductResponse productResponse = productService.createProduct(request);
        //then
        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2)
            .extracting("productNumber", "type", "sellingStatus", "price", "name")
            .containsExactlyInAnyOrder(
                tuple("001", HANDMADE, SELLING, 4000, "아메리카노"),
                tuple("002", HANDMADE, SELLING, 5000, "카푸치노"));

    }

    @Test
    @DisplayName("상품이 하나도 없는 경우에서, 신규 상품을 등록하면 상품번호는 001이다.")
    public void createProductWhenProductsIsEmpty() throws Exception {
        //given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5000)
            .build();
        //when -> 보통한줄 행위를 정의
        ProductResponse productResponse = productService.createProduct(request);
        //then
        assertThat(productResponse)
            .extracting("productNumber", "type", "sellingStatus", "price", "name")
            .contains("001", HANDMADE, SELLING, 5000, "카푸치노");

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
            .extracting("productNumber", "type", "sellingStatus", "price", "name")
            .contains(
                tuple("001", HANDMADE, SELLING, 5000, "카푸치노"));
    }

    private Product createProduct(String productNumber, ProductType type,
        ProductSellingStatus sellingStatus, String name, int price) {
        Product product1 = Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
        return product1;
    }
}