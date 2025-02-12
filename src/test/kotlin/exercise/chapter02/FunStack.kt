package exercise.chapter02

data class FunStack<TYPE>(
    private val elements: Array<Any?> = Array(1) { null },
    private val index: Int = 0
) {
    val size: Int
        get() = index

    /**
     * 원소를 push하면 새 스택이 반환된다.
     * */
    fun push(value: TYPE): FunStack<TYPE> {
        val nextSize = elements.size + 1
        val nextElements = elements.copyOf(nextSize)

        nextElements[index] = value

        return FunStack(nextElements, index + 1)
    }

    /**
     * pop을 하면 pop한 원소와 새 스택이 반환된다.
     * */
    @Suppress("UNCHECKED_CAST")
    fun pop(): Pair<TYPE, FunStack<TYPE>> {
        val nextIndex = index - 1
        val v = elements[nextIndex]

        val copy = elements.copyOf(elements.size - 1)

        return (v as TYPE) to FunStack(copy, nextIndex)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FunStack<*>

        if (!elements.contentEquals(other.elements)) return false
        if (index != other.index) return false

        return true
    }

    override fun hashCode(): Int {
        var result = elements.contentHashCode()
        result = 31 * result + index
        return result
    }

}