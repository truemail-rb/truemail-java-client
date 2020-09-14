import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MainTests {

    @Test
    @DisplayName("1 + 1 = 2")
    void addsTwoNumbers() {
        Main main = new Main();
        assertEquals(2, main.add(1, 1), "1 + 1 should equal 2");
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "0,    1,   1",
            "1,    2,   3",
            "49,  51, 100",
            "1,  100, 101"
    })
    void add(int first, int second, int expectedResult) {
        Main main = new Main();
        assertEquals(expectedResult, main.add(first, second),
                () -> first + " + " + second + " should equal " + expectedResult);
    }
}