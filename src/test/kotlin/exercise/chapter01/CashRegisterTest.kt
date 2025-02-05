package exercise.chapter01

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.floor

class CashRegister(
    private val priceInfo: Map<String, Double>,
    private val promotionInfo: Map<String, String>
) {
    fun checkout(vararg foods: String): Double {
        val buyMap = foods.groupingBy { it }.eachCount()
        var totalPrice = 0.0
        buyMap.forEach { (food, foodCount) ->
            val price = priceInfo[food] ?: throw IllegalArgumentException("Invalid food: $food")
            val promotion = promotionInfo[food] ?: "1x1"

            val (ifBuyCount, thenCount) = promotion.split("x").let { it[0].toIntOrNull() to it[1].toIntOrNull() }
            if(ifBuyCount == null) throw IllegalArgumentException("Invalid ifBuyCount: $ifBuyCount")
            if(thenCount == null) throw IllegalArgumentException("Invalid thenCount: $thenCount")

            if(foodCount >= ifBuyCount) {
                val promotionCount = foodCount / ifBuyCount
                val remainCount = foodCount % ifBuyCount
                totalPrice += (promotionCount * thenCount * price) + (remainCount * price)
            } else {
                totalPrice += foodCount * price
            }
        }
        return totalPrice
    }
}

class CashRegisterTest {
    private fun makePromotion(number: Int): String {
        return "${number}x${number - 1}"
    }

    @Test
    fun `checkout test`() {
        // given
        val applePrice = String.format("%.1f", randomDoubleNatural()).toDouble()
        println("applePrice = $applePrice")
        val orangePrice = String.format("%.1f", randomDoubleNatural()).toDouble()
        println("orangePrice = $orangePrice")

        val applePromotionCnt = randomIntNatural()
        println("applePromotionCnt = $applePromotionCnt")
        val applePromotion = makePromotion(applePromotionCnt)
        val apples = Array(applePromotionCnt) { "apple" }

        val orangePromotionCnt = randomIntNatural()
        println("orangePromotionCnt = $orangePromotionCnt")
        val orangePromotion = makePromotion(orangePromotionCnt)
        val oranges = Array(orangePromotionCnt) { "orange" }

        // when
        val register = CashRegister(
            mapOf("apple" to applePrice, "orange" to orangePrice),
            mapOf("apple" to applePromotion, "orange" to orangePromotion)
        )

        // then
        expectThat(register.checkout(*apples)).isEqualTo(applePrice * (applePromotionCnt - 1))
        expectThat(register.checkout(*oranges)).isEqualTo(orangePrice * (orangePromotionCnt - 1))

        expectThat(register.checkout(*apples, "apple")).isEqualTo((applePrice * (applePromotionCnt - 1)) + applePrice)
    }
}