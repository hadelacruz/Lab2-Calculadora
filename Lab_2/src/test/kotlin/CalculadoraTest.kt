import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalculadoraTest {

    @Test
    fun testEvaluateSimpleAddition() {
        val calculadora = Calculadora()
        val infixExpression = "3 + 4"
        val postfixExpression = calculadora.infixToPostfix(infixExpression)
        val result = calculadora.evaluatePostfix(calculadora.stringToList(postfixExpression))
        assertEquals(7.0, result)
    }

    @Test
    fun testEvaluateComplexExpression1() {
        val calculadora = Calculadora()
        val infixExpression = "16^(1/2)+10"
        val postfixExpression = calculadora.infixToPostfix(infixExpression)
        val result = calculadora.evaluatePostfix(calculadora.stringToList(postfixExpression))
        assertEquals(14.0, result)
    }

    @Test
    fun testEvaluateComplexExpression2() {
        val calculadora = Calculadora()
        val infixExpression = "100 * 2 + 12"
        val postfixExpression = calculadora.infixToPostfix(infixExpression)
        val result = calculadora.evaluatePostfix(calculadora.stringToList(postfixExpression))
        assertEquals(212.0, result)
    }
}