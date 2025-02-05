package exercise.chapter01

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AdditionTest {
    @Test
    fun `add two numbers`() {
        expectThat(5 + 6).isEqualTo(11)
        expectThat(7 + 42).isEqualTo(49)
        expectThat(9999 + 1).isEqualTo(10_000)
    }

    @Test
    fun `zero identity`() {
        repeat(100) {
            val x = randomIntNatural()
            expectThat(x + 0).isEqualTo(x)
        }
    }

    @Test
    fun `commutative property`() {
        repeat(100) {
            val x = randomIntNatural()
            val y = randomIntNatural()
            expectThat(x + y).isEqualTo(x + y)
        }
    }

    @Test
    fun `associative property`() {
        repeat(100) {
            val x = randomIntNatural()
            val y = randomIntNatural()
            val z = randomIntNatural()
            expectThat((x + y) + z).isEqualTo(x + (y + z))
            expectThat((y + z) + x).isEqualTo(y + (z + x))
            expectThat((z + x) + y).isEqualTo(z + (x + y))
        }
    }
}