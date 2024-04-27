package sample.cafekiosk.spring.api.controller.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

/*mocking시 필요한 주석으로, controller layer만 테스트하기위한 빈들만 올리는 가벼운 테스트 주석*/
class ProductControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("신규 상품을 등록한다.")
    public void createProduct() throws Exception {
        //given api 활용
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.SELLING)
            .price(4000)
            .name("아메리카노")
            .build();

        //when        //then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())//log 확인
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수입니다.")
    public void createProductWithoutType() throws Exception {
        //given api 활용
        ProductCreateRequest request = ProductCreateRequest.builder()
            .sellingStatus(ProductSellingStatus.SELLING)
            .price(4000)
            .name("아메리카노")
            .build();

        //when        //then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())//log 확인
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 판매상태는 필수입니다.")
    public void createProductWithoutSellingStatus() throws Exception {
        //given api 활용
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(ProductType.HANDMADE)
            .price(4000)
            .name("아메리카노")
            .build();

        //when        //then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())//log 확인
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 이름은 필수입니다.")
    public void createProductWithoutName() throws Exception {
        //given api 활용
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.SELLING)
            .price(4000)
            .build();

        //when        //then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())//log 확인
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 가격은 양수다.")
    public void createProductWithoutZeroPrice() throws Exception {
        //given api 활용
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingStatus.SELLING)
            .name("아메리카노")
            .price(0)
            .build();

        //when        //then
        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())//log 확인
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 가격은 양수여야합니다."))
            .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @Test
    @DisplayName("판매 상품을 조회한다.")
    public void getSellingProducts() throws Exception {
        //when ->서비스레이어에서 테스트한 부분은 제외하고 배열이 잘 오는지만 확인
        List<ProductResponse> result = List.of();

        when(productService.getSellingProducts()).thenReturn(result);

        // then
        /*
         * 조회 테스트시 쿼리파라미터가 붙는다면
         * 아래처럼하고 .queryParam("name","이름").queryParam()
         * */
        mockMvc.perform(
                get("/api/v1/products/selling")
            )
            .andDo(print())//log 확인
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isArray())
        ;
    }
}