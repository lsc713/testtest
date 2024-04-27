package sample.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class StockTest {

    @DisplayName("재고의 수량이 제공된 수량보다 적은지 확인")
    @Test
    public void isQuantityLessThan() throws Exception {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when
        boolean result = stock.isQuantityLessThan(quantity);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("재고를 주어진 갯수만큼 차감할 수 있다.")
    public void deductQuantity() throws Exception {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
    public void deductQuantity2() throws Exception {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when //then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("차감할 재고 수량이 없습니다.");
    }

    @DisplayName("")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynamicTest() {
        //given
        Stock stock = Stock.create("001", 1);
        return List.of(
            DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.",()->{
                //given
                int quantity = 1;
                //when
                stock.deductQuantity(quantity);
                //then
                assertThat(stock.getQuantity()).isZero();
            }),
            DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.",()->{
                //given
                int quantity = 1;

                //when //then
                assertThatThrownBy(() -> stock.deductQuantity(quantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("차감할 재고 수량이 없습니다.");
            })
        );
    }
}