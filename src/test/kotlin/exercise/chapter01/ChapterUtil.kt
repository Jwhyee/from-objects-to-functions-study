package exercise.chapter01

import kotlin.random.Random

fun randomIntNatural(
    until: Int = 1000_000_000
) = Random.nextInt(from = 1, until = until)

fun randomDoubleNatural(
    until: Double = 1000_000_000.0
) = Random.nextDouble(from = 1.0, until = until)