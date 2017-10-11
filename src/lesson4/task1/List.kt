@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson3.task1.digitNumber
import lesson3.task1.getDigits
import lesson3.task1.minDivisor
import lesson3.task1.pow
import lesson1.task1.discriminant

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var ret = 0.0
    for (el in v)
        ret += el * el

    return Math.sqrt(ret)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    val size = list.size
    if (size == 0) return 0.0

    return list.sum() / size
}


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val cen = mean(list)
    for (i in 0..list.lastIndex)
        list[i] -= cen

    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var res = 0.0
    for (i in 0..a.lastIndex)
        res += a[i] * b[i]

    return res
}


/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var res = 0.0
    for ((index, el) in p.withIndex())
        res += el * Math.pow(x, index.toDouble())

    return res
}


/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in 1..list.lastIndex)
        list[i] = list[i] + list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val list = mutableListOf<Int>()
    var number = n

    while (number > 1) {
        val minDiv = minDivisor(number)
        list.add(minDiv)
        number /= minDiv
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int) = factorize(n).joinToString("*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var a = n
    while (a != 0) {
        list.add(a % base)
        a /= base
    }
    if (list.isEmpty()) list += 0
    return list.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val list = convert(n, base)
    val str = StringBuilder()
    for (el in list)
        if (el < 10)
            str.append(el.toString())
        else str.append('a' + el - 10)

    return str.toString()
}


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var res = 0
    for ((i, di) in digits.withIndex())
        res += di * pow(base, digits.lastIndex - i)
    return res
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val list = mutableListOf<Int>()
    for (ch in str) {
        if (ch in '0'..'9')
            list.add(ch - '0')
        else list.add(ch - 'a' + 10)
    }

    return decimal(list, base)
}


fun romanHelp(n: Int, digit: Int): List<Int> {
    val di = pow(10, digit)
    return when (n) {
        1 -> listOf(di)
        2 -> listOf(di, di)
        3 -> listOf(di, di, di)
        4 -> listOf(di, 5 * di)
        5 -> listOf(5 * di)
        6 -> listOf(5 * di, di)
        7 -> listOf(5 * di, di, di)
        8 -> listOf(5 * di, di, di, di)
        9 -> listOf(di, 10 * di)
        else -> listOf()
    }
}


/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var list = listOf<Int>()
    val th = n % 1000
    val di = digitNumber(th) - 1
    val str = StringBuilder()
    val digits = getDigits(th)

    for (i in di downTo 0)
        list += romanHelp(digits[di - i], i)

    for (i in 1..n / 1000) str.append("M") //incorrect

    for (i in list)
        str.append(when (i) {
            1 -> "I"
            5 -> "V"
            10 -> "X"
            50 -> "L"
            100 -> "C"
            500 -> "D"
            1000 -> "M"
            else -> ""
        })
    return str.toString()
}

//3 цифры
fun numberR(numb3: Int): String =
        when (numb3 / 100) {
            9 -> "девятьсот "
            8 -> "восемьсот "
            7 -> "семьсот "
            6 -> "шестьсот "
            5 -> "пятьсот "
            4 -> "четыреста "
            3 -> "триста "
            2 -> "двести "
            1 -> "сто "
            0 -> ""
            else -> throw Exception("numb3 = $numb3")
        } + decadeR(numb3 % 100)

//2 цифры
fun decadeR(numb2: Int): String =
        when ((numb2 / 10) % 10) {
            9 -> "девяносто "
            8 -> "восемьдесят "
            7 -> "семьдесят "
            6 -> "шестьдесят "
            5 -> "пятьдесят "
            4 -> "сорок "
            3 -> "тридцать "
            2 -> "двадцать "
            1 -> when (numb2 % 10) {
                9 -> "девятнадцать "
                8 -> "восемнадцать "
                7 -> "семнадцать "
                6 -> "шестнадцать "
                5 -> "пятнадцать "
                4 -> "четырнадцать "
                3 -> "тринадцать "
                2 -> "двенадцать "
                1 -> "одиннадцать "
                else -> "десять "
            }
            else -> ""
        }

fun unitsR(numb: Int): String =
        if ((numb / 10) % 10 != 1)
            when (numb % 10) {
                9 -> "девять "
                8 -> "восемь "
                7 -> "семь "
                6 -> "шесть "
                5 -> "пять "
                4 -> "четыре "
                3 -> "три "
                2 -> "два "
                1 -> "один "
                else -> ""
            }
        else ""

fun thousandR(numb: Int): String =
        if ((numb / 10) % 10 != 1)
            when (numb % 10) {
                9 -> "девять тысяч "
                8 -> "восемь тысяч "
                7 -> "семь тысяч "
                6 -> "шесть тысяч "
                5 -> "пять тысяч "
                4 -> "четыре тысячи "
                3 -> "три тысячи "
                2 -> "две тысячи "
                1 -> "одна тысяча "
                else -> "тысяч "
            }
        else "тысяч "

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val first = n / 1000
    val second = n % 1000
    val str = StringBuilder()

    //>= 1000
    if (first != 0) str.append(numberR(first) + thousandR(first))
    //< 1000
    str.append(numberR(second) + unitsR(second))

    return str.toString().trim()
}