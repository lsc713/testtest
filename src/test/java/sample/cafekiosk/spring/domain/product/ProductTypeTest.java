package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
    public void containsStockType() throws Exception {
        //given

        ProductType givenType = ProductType.HANDMADE;
        //when
        boolean result = ProductType.containsStockType(givenType);

        //then
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
    public void containsStockType2() throws Exception {
        //given

        ProductType givenType = ProductType.BAKERY;
        //when
        boolean result = ProductType.containsStockType(givenType);

        //then
        assertThat(result).isTrue();

    }

    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다")
    @Test
    public void containsStockType3() throws Exception{
        //given
        ProductType givenType1 = ProductType.HANDMADE;
        ProductType givenType2 = ProductType.BOTTLE;
        ProductType givenType3 = ProductType.BAKERY;

        //when
        boolean result1 = ProductType.containsStockType(givenType1);
        boolean result2 = ProductType.containsStockType(givenType2);
        boolean result3 = ProductType.containsStockType(givenType3);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다")
    @ParameterizedTest
    @CsvSource({"HANDMADE,false","BOTTLE,true","BAKERY,true"})
    public void containsStockType4(ProductType productType, boolean expected) throws Exception{
        //when
        boolean result = ProductType.containsStockType(productType);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
            Arguments.of(ProductType.HANDMADE, false),
            Arguments.of(ProductType.BOTTLE, true),
            Arguments.of(ProductType.BAKERY, true)
        );
    }

    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다")
    @ParameterizedTest
    @MethodSource("provideProductTypesForCheckingStockType")
    public void containsStockType5(ProductType productType, boolean expected) throws Exception{
        //when
        boolean result = ProductType.containsStockType(productType);

        //then
        assertThat(result).isEqualTo(expected);
    }

}