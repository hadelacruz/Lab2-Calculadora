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
}