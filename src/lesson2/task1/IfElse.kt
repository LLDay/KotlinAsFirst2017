@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    var str: String = age.toString()
    val last = age % 10
    when
    {
        (age / 10) % 10 == 1 -> str += " лет"
        last >= 5 -> str += " лет"
        last >= 2 -> str += " года"
        last == 1 -> str += " год"
        last == 0 -> str += " лет"
    }
    return str
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val way1 = t1 * v1
    val way2 = t2 * v2
    val way3 = t3 * v3

    val way = way1 + way2 + way3 / 2.0

    val time: Double

    when
    {
        way > way1 + way2 -> time = t1 + t2 + (way - way1 - way2) / v3
        way > way1        -> time = t1 + (way - way1) / v2
        else              -> time = way / v1
    }

    return time
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int
{
    var res = 0
    if (kingX == rookX1 || kingY == rookY1) res += 1
    if (kingX == rookX2 || kingY == rookY2) res += 1

    return res
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int
{
    var res = 0
    if (kingX == rookX || kingY == rookY) res += 1
    if (ads(kingX - bishopX) == ads(kingY - bishopY)) res += 2
    return res
}


/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int
{
    if (a <= 0 || b <= 0 || c <= 0) return -1
    // a*a = b*b + c*c - 2*b*c*Cos(a)
    // Cos(a) = (a*a - b*b - c*c) / (2*b*c)

    val angA = arccos(a*a - b*b - c*c) / (2*b*c)
    swap(a, b)
    val angB = arccos(a*a - b*b - c*c) / (2*b*c)
    swap(a, c)
    val angC = arccos(a*a - b*b - c*c) / (2*b*c)

    if (angA + angB + angC != 180) return -1
    if (angA > 90 || angB > 90 || angC > 90) return 2
    if (angA == 90 || angB == 90 || angC == 90) return 1
    return 0
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int
{
    if (d > b)
    {
        swap(b, d)
        swap(a, c)
    }
    val res = min(a, c) - d
    if (res <= 0) return -1
    else return res
}

