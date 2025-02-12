package exercise.chapter02

import java.util.*

object RpnCalc {
    /**
     * RPN은 연산자가 피연산자 뒤에 오는 수학 표기법
     * 연산자를 두 피연산자의 중간에 배치한다.
     * 우선 순위를 표기하기 위해 괄호 대신 피연산자 뒤에 연산자를 배치한다.
     * */
    fun calc(expression: String): Double {
        val result = StringTokenizer(expression)
            .toList()
            .fold(FunStack<Double>()) { acc, v ->
                val funStack = "$v".toDoubleOrNull()?.let { d ->
                    acc.push(d)
                } ?: run {
                    val (v1, stack1) = acc.pop()
                    val (v2, stack2) = stack1.pop()
                    val result = when(v) {
                        "+" -> { v2 + v1 }
                        "-" -> { v2 - v1 }
                        "/" -> { v2 / v1 }
                        "*" -> { v2 * v1 }
                        else -> throw IllegalArgumentException("Invalid Operation")
                    }
                    stack2.push(result)
                }
                funStack
            }
        return result.pop().first
    }
}