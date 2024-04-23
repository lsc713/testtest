package sample.cafekiosk.unit.beverages;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();
//        assertEquals(americano.getName(), "Americano");
        assertThat(americano.getName()).isEqualTo("Americano");
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}