package exercise.chapter02

import java.util.*

object RpnCalc {
    private val operationsMap = mapOf<String, (Double, Double) -> Double>(
        "+" to Double::plus,
        "-" to Double::minus,
        "/" to Double::div,
        "*" to Double::times
    )

    fun calc(expression: String): Double {
        val funStack = FunStack<Double>()
        return StringTokenizer(expression).toList().map { "$it" }
            .fold(funStack, ::reduce)
            .pop().first
    }

    /**
     * RPN은 연산자가 피연산자 뒤에 오는 수학 표기법
     * 연산자를 두 피연산자의 중간에 배치한다.
     * 우선 순위를 표기하기 위해 괄호 대신 피연산자 뒤에 연산자를 배치한다.
     * */
    private fun reduce(stack: FunStack<Double>, token: String): FunStack<Double> =
        if(operationsMap.containsKey(token)) {
            val (b, prevStack) = stack.pop()
            val (a, newStack) = prevStack.pop()
            newStack.push(operationsMap[token]!!(a, b))
        } else {
            stack.push(token.toDouble())
        }
}