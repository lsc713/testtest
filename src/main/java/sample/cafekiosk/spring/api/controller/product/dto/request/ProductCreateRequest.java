package sample.cafekiosk.spring.api.controller.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    /*
     *NOTBLANK -> 공백 빈문자 통과불가 String->NOTBLANK
     * NOTNULL -> "" " " 통과 -> ENUM이므로
     * NOTEMPTY -> "   " 통과 ""통과불가
     *
     *TODO
     * VALIDATION 책임 분리 (특수하지 않다면(필요최소한은) 컨트롤러 / 정책에 의해 특수한 경우에는(서비스레이어에서 또는 생성자생성시)
     * String name -> 상품 이름은 20자로 제한하자라고 예를들면
     * @Max(20) 주석을 통해 문자열제한이 가능은하지만 컨트롤러(앞단)에서하는게 옳은가
     *
     *
     * */
    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;
    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;
    @Positive(message = "상품 가격은 양수여야합니다.")
    private int price;
    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, int price,
        String name) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.price = price;
        this.name = name;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }
}
