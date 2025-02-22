package exercise.chapter02

data class FunStack<TYPE>(
    private val elements: List<TYPE> = emptyList()
) {
    fun size() = elements.size

    fun push(value: TYPE) = FunStack(listOf(value) + elements)

    fun pop() = elements.firstOrNull()?.let {
        it to FunStack(elements.drop(1))
    } ?: throw IllegalStateException("스택이 비어있습니다.")
}