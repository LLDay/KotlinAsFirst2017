@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1


/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E> (height: Int, width: Int, elem: E) : Matrix<E> {
    private val matrix = emptyList<MutableList<E>>().toMutableList()
    init {
        if (height <= 0 || width <= 0)
            throw IllegalArgumentException("")

        for (i in 0 until height) {
            val column = mutableListOf<E>()
            for (j in 0 until width)
                column.add(elem)
            this.matrix.add(column)
        }
    }

    override val height: Int = matrix.size

    override val width: Int = matrix[0].size

    override fun get(row: Int, column: Int): E = matrix[row][column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        matrix[row][column] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean = other is MatrixImpl<*> && other.matrix == this.matrix

    override fun toString() = "height = ${this.height} width = ${this.width}"

    override fun hashCode(): Int {
        var result = matrix.hashCode()
        result = 31 * result + height
        result = 31 * result + width
        return result
    }
}

