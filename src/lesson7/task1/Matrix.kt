@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1


/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int) {
    fun up()    = Cell(row - 1, column)
    fun down()  = Cell(row + 1, column)

    fun right() = Cell(row, column + 1)
    fun left()  = Cell(row, column - 1)
}

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

    fun toList(): List<List<E>>

    fun contains(row: Int, column: Int): Boolean
    fun contains(cell: Cell): Boolean

    fun swap(first: Cell, second: Cell)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

fun <E> createMatrix(height: Int, width: Int, e: List<List<E>>) : Matrix<E> {
    if (e.isEmpty() || e[0].isEmpty())
        throw IllegalArgumentException("Empty Matrix")

    if (height != e.size || width != e[0].size)
        throw IllegalArgumentException("incomplete list of arguments")

    val matrix = MatrixImpl(height, width, e[0][0])

    for (i in 0..e.lastIndex)
        for (j in 0..e[i].lastIndex)
            matrix[i, j] = e[i][j]

    return matrix
}

fun <E> createMatrix(e: List<List<E>>) : Matrix<E> {
    if (e.isEmpty() || e[0].isEmpty())
        throw IllegalArgumentException("Empty Matrix")

    return createMatrix(e.size, e[0].size, e)
}


/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */

class MatrixImpl<E> (height: Int, width: Int, elem: E) : Matrix<E> {
    private val matrix = mutableListOf< MutableList<E> >()
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


    override fun equals(other: Any?) = other is MatrixImpl<*> && other.matrix == this.matrix

    override fun toString(): String {
        val str = StringBuilder("height = ${this.height} width = ${this.width}\n")

        for (line in matrix) {
            str.append("| ")
            for (el in line)
                str.append("${el.toString()} | ")
            str.append('\n')
        }

        return str.toString()
    }

    override fun toList(): List<List<E>> = matrix

    override fun contains(row: Int, column: Int): Boolean = row in 0 until height && column in 0 until width

    override fun contains(cell: Cell): Boolean = contains(cell.row, cell.column)

    override fun swap(first: Cell, second: Cell) {
        val tmp = this[first]
        this[first] = this[second]
        this[second] = tmp
    }

    override fun hashCode(): Int {
        var result = matrix.hashCode()
        result = 31 * result + height
        result = 31 * result + width
        return result
    }
}

