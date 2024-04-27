package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.IntegrationTestSupport;

//@DataJpaTest/*tx이 있음. Springboottest는 없어서 aftereach필요*/
@Transactional
class ProductRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 가져온다.")
    @Test
    public void findAllBySellingStatusIn() throws Exception {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        //when

        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));
        //then

        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            );

    }

    @DisplayName("상품번호 리스트로 상품들을 가져온다.")
    @Test
    public void findAllByProductNumberIn() throws Exception {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        //when

        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));
        //then

        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            );
    }

    @DisplayName("가장 마지막으로 저장된 상품의 상품번호를 읽어온다.")
    @Test
    public void findLatestProductNumber() throws Exception {
        //given
        String targetProductNumber = "003";

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        //when

        String latestProductNumber = productRepository.findLatestProductNumber();
        //then

        assertThat(latestProductNumber).isEqualTo(targetProductNumber);

    }

    @DisplayName("가장 마지막으로 저장된 상품의 상품번호를 읽어오는데, 상품이 하나도 업는 경우에는 null을 반환한다..")
    @Test
    public void findLatestProductNumberWhenProductIsEmpty() throws Exception {
        //when
        String latestProductNumber = productRepository.findLatestProductNumber();
        //then
        assertThat(latestProductNumber).isNull();
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