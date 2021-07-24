import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCalculator {
    @Test
    public void testing(){
        assertEquals("8.550000000000",Logic.calculate("(3*(4-1))-5%"));
        assertEquals("-0.110869565218",Logic.calculate("(3*((4-5)/23))-15%"));
    }
}
