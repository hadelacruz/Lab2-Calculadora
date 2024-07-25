import java.util.Scanner
import java.util.Stack
import kotlin.math.*

class Calculadora {

    /**
     * Método para determinar la precedencia de un operador.
     *
     * @param c El operador cuya precedencia se desea conocer.
     * @return El nivel de precedencia del operador, o -1 si el operador no es reconocido.
     */
    fun precedence(c: Char): Int {
        return when (c) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            else -> -1
        }
    }

    /**
     * Convierte una expresión infix a postfix.
     *
     * @param expresion La expresión en formato infix.
     * @return La expresión convertida en formato postfix.
     */
    fun infixToPostfix(expresion: String): String {
        val result = StringBuilder()
        val stack = Stack<Char>()

        val cleanedExpression = expresion.replace("\\s+".toRegex(), "")

        var i = 0
        while (i < cleanedExpression.length) {
            val c = cleanedExpression[i]

            if (c.isLetterOrDigit()) {
                while (i < cleanedExpression.length && cleanedExpression[i].isLetterOrDigit()) {
                    result.append(cleanedExpression[i])
                    i++
                }
                result.append(' ')
                i--
            } else if (c == '(') {
                stack.push(c)
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result.append(stack.pop()).append(' ')
                if (!stack.isEmpty() && stack.peek() != '(') {
                    return "Invalid Expression" // expresión inválida
                } else {
                    stack.pop()
                }
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()))
                    result.append(stack.pop()).append(' ')
                stack.push(c)
            }
            i++
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid Expression" // expresión inválida
            result.append(stack.pop()).append(' ')
        }

        return result.toString().trim()
    }

    fun stringToList(expression: String): List<String> {
        return expression.split(" ").filter { it.isNotEmpty() }
    }

    // Method to evaluate a postfix expression
    fun evaluatePostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()

        for (token in postfix) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                else -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> a / b
                        "^" -> a.pow(b)
                        "sqrt" -> sqrt(b)
                        "exp" -> exp(b)
                        else -> throw IllegalArgumentException("Unknown operator: $token")
                    })
                }
            }
        }
        return stack.pop()
    }
}

/**
 * Lee expresiones infix desde la terminal, las convierte a postfix y devuelve el contenido modificado.
 */
fun main() {
    val calculadora = Calculadora()
    val scanner = Scanner(System.`in`)
    val modifiedContent = StringBuilder()
    var exit = false

    while (!exit) {
        println("Menú:")
        println("1. Agregar una expresión infix")
        println("2. Salir")
        print("Ingrese su opción: ")

        when (scanner.nextLine()) {
            "1" -> {
                print("Ingrese la expresión infix: ")
                val expresion = scanner.nextLine()
                if (expresion.isNotBlank()) {
                    val postfixExpression: String = calculadora.infixToPostfix(expresion)
                    val result = calculadora.evaluatePostfix(calculadora.stringToList(postfixExpression))
                    modifiedContent.append(postfixExpression).append("\n")
                    println("Resultado: $result")
                } else {
                    println("Expresión vacía. Intente de nuevo.")
                }
            }
            "2" -> {
                exit = true
                println("Saliendo del programa.")
            }
            else -> {
                println("Opción no válida. Intente de nuevo.")
            }
        }
    }
}