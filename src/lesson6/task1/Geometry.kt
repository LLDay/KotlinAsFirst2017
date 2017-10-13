@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val dis = center.distance(other.center) - (radius + other.radius)

        if (dis < 0) return 0.0
        return dis
    }

    fun isBelong(point:Point) = this.center.distance(point) <= this.radius

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2)
        throw IllegalArgumentException("")

    var max = 0.0
    var seg = Segment(Point(0.0, 0.0), Point(0.0, 0.0))

    for (i in 0 until points.lastIndex)
        for (j in i + 1..points.lastIndex) {
            val distance = points[i].distance(points[j])
            if (max < distance) {
                seg = Segment(points[i], points[j])
                max = distance
            }
        }

    return seg
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val x = (diameter.begin.x + diameter.end.x) / 2.0
    val y = (diameter.begin.y + diameter.end.y) / 2.0
    val radius = diameter.begin.distance(diameter.end) / 2.0

    return Circle(Point(x, y), radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)


    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        //Hardest
        if (this.angle == other.angle)
            throw IllegalArgumentException("The lines are parallel")

        val sinA = Math.sin(this.angle)
        val sinB = Math.sin(other.angle)

        val cosA = Math.cos(this.angle)
        val cosB = Math.cos(other.angle)

        val b1 = this.b * cosB
        val b2 = other.b * cosA

        val x: Double
        val y: Double

        when {
            Math.abs(sinA) < 1E-15 -> { //this 0°
                x = (b1 - b2) / (sinB * cosA)
                y = (x * sinB + other.b) / cosB
            }
            Math.abs(cosA) < 1E-15 -> { //this 90°
                x = -this.b / sinA
                y = (x * sinB + other.b) / cosB
            }
            Math.abs(sinB) < 1E-15 -> { //other 0°
                x = (b2 - b1) / (sinA * cosB)
                y = (x * sinA + this.b) / cosA
            }
            Math.abs(cosB) < 1E-15 -> { //other 90°
                x = -other.b / sinB
                y = (x * sinA + this.b) / cosA
            }
            else -> {
                x = (b2 - b1) / Math.sin(this.angle - other.angle)
                y = (x * sinA + this.b) / cosA
            }
        }
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    if (s.begin == s.end)
        throw IllegalArgumentException("It is impossible to build the line at one point")

    var angle = Math.atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x))

    if (angle < 0.0) angle += Math.PI

    return Line(s.begin, angle)
}


/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val center = Point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0)
    var angle = lineByPoints(a, b).angle + Math.PI / 2.0
    angle %= Math.PI

    return Line(center, angle)
}

fun bisectorByPoints(s: Segment) = bisectorByPoints(s.begin, s.end)


/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    val lastIndex = circles.lastIndex
    if (lastIndex < 1)
        throw IllegalArgumentException("")

    var cFirst = circles[0]
    var cSecond = circles[1]
    var distance = cFirst.distance(cSecond)
    var minDistance = distance
    var pair = Pair(circles[0], circles[1])

    for (i in 0 until lastIndex) {
        cFirst = circles[i]
        for (j in (i + 1)..lastIndex) {
            cSecond = circles[j]
            distance = cFirst.distance(cSecond)

            if (distance < minDistance) {
                pair = Pair(cFirst, cSecond)
                minDistance = distance
            }
        }
    }

    return pair
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val s1 = Segment(a, b)
    val s2 = Segment(b, c)

    val center = bisectorByPoints(s1).crossPoint(bisectorByPoints(s2))
    val radius = a.distance(center)

    return Circle(center, radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty())
        throw IllegalArgumentException("Array is empty")

    var circleRes = Circle(points[0], Double.MAX_VALUE)

    if (points.size == 2)
        circleRes = circleByDiameter(Segment(points[0], points[1]))

    for (i in 0..points.lastIndex - 2)
        for (j in i + 1 until points.lastIndex)
            for (k in j + 1..points.lastIndex) {
                val a = points[i]
                val b = points[j]
                val c = points[k]

                val circles = listOf(
                        circleByThreePoints(a, b, c),
                        circleByDiameter(Segment(a, b)),
                        circleByDiameter(Segment(b, c)),
                        circleByDiameter(Segment(c, a))
                )

                for (circle in circles) {
                    var belongs = true

                    for (point in points) {
                        if (!circle.isBelong(point))
                            belongs = false
                    }

                    if (belongs && circleRes.radius > circle.radius)
                            circleRes = circle
                }
            }

    return circleRes
}