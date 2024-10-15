package es.ucm.fdi.advancedcalculator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalculatorUnitTest {

    @Test
    public void testSumDoubles() {
        // Crear una instancia de la clase Calculator
        Calculator calculator = new Calculator();

        // Probar la suma de doubles
        double result = calculator.sum(2.0, 3.0);
        assertEquals(5.0, result, 0.0001); //
    }


}
