@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

//Вспомогательная
fun powInt(x: Int, n: Int): Int = when (n) {
    1 -> x
    0 -> 1
    else -> x * powInt(x, n - 1)
}

//Вспомогательная
fun powInt(x: Double, n: Int): Double = when (n) {
    1 -> x
    0 -> 1.0
    else -> x * powInt(x, n - 1)
}

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    if (n == 0) return 1

    var n1 = n
    if (n1 < 0) n1 *= -1

    var count = 0

    while (n1 > 0) {
        n1 /= 10
        count++
    }

    return count
}


/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    //It is my
    //if (n <= 2) return 1
    //return fib(n - 2) + fib (n - 1)

    //Google is my love
    val f = (1.0 + Math.sqrt(5.0)) / 2.0
    return (Math.pow(f, n.toDouble()) / Math.sqrt(5.0) + 0.5).toInt()

    //TEST::fib(50) = 2147483647
}

//НОД
fun gcd(m: Int, n: Int): Int {
    var m1 = m
    var n1 = n
    while (m1 != 0 && n1 != 0 && m1 != n1) {
        if (m1 >= n1) m1 %= n1
        else n1 %= m1
    }
    return Math.max(m1, n1)
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int = m * n / gcd(m, n)


/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (n % 2 == 0) return 2
    var tmp = 3
    val thr = Math.sqrt(n.toDouble()).toInt() //Порог, выше не имеет смысла

    while (tmp < thr && n % tmp != 0) tmp += 2
    if (n % tmp != 0) return n
    return tmp
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)


/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = gcd(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    //[√n] != [√m] => между m и n есть целый квадрат
    if (m != n)
        return (Math.sqrt(m.toDouble())).toInt() != (Math.sqrt(n.toDouble())).toInt()

    val tmp = (Math.sqrt(m.toDouble())).toInt()
    return tmp * tmp == m
}

// if (sin) plus = 1
// if (cos) plus = 0
fun sinHelper(x: Double, n: Int): Double {
    val powL = Math.pow(Math.abs(x), 2.0 * n + 1)
    val factR = factorial(2 * n + 1)

    return powL / factR
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x^1 / 1! - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var a = x

    while (a >= 2 * Math.PI) a -= 2 * Math.PI
    while (a <= -2 * Math.PI) a += 2 * Math.PI

    var res = 0.0
    var n = 0
    var thr = sinHelper(a, n)

    while (thr >= eps) {
        if (n % 2 == 0) res += thr //Каждое четное место
        else res -= thr
        thr = sinHelper(a, ++n)
    }
    return if (a >= 0) res
    else -res
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = (0) x^0 / 0! - (1) x^2 / 2! + (2) x^4 / 4! - (3) x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    //Cos(-x) = Cos(x)
    var a = Math.abs(x) //a = [minDouble; maxDouble]
    while (a >= 2 * Math.PI) a -= 2.0 * Math.PI //a = [0; 2Pi)

    //Cos(x) = Cos(2Pi - x)
    if (a > Math.PI) a = 2.0 * Math.PI - a //a = [0; Pi)

    a -= Math.PI / 2.0 //a = [-Pi/2; Pi/2)
    //Cos(x) = Cos(a + Pi/2) = -Sin(a)

    return -sin(a, eps)
}

fun getNumber(n: Int, index: Int): Int {
    //Возвращает цифру числа
    //Индексация цифр слева направо с 0
    val count = digitNumber(n)
    if (count <= index)
        throw Exception("Bad index\n" +
                "Digit of number = $count\n" +
                "Index = $index")

    var n1 = n
    for (i in 1..(count - index - 1))
        n1 /= 10

    return n1 % 10
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 ->  87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var res = 0
    for (i in 0 until digitNumber(n))
        res += getNumber(n, i) * powInt(10, i)
    return res
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */


fun isPalindrome(n: Int): Boolean {
    val last = digitNumber(n) - 1
    for (i in 0..last) {
        if (i > last / 2) break
        if (getNumber(n, i) != getNumber(n, last - i)) return false
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val last = digitNumber(n) - 1
    val firstN = getNumber(n, 0)
    for (i in 0..last)
        if (getNumber(n, i) != firstN)
            return true
    return false
}


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var q = 0
    var numb = 0
    var preNumb = numb


    while (numb < n) {
        preNumb = numb
        numb += digitNumber(powInt(++q, 2))
    }
    return getNumber(powInt(q, 2), n - preNumb - 1)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var q = 0
    var numb = 0
    var preNumb = numb


    while (numb < n) {
        preNumb = numb
        numb += digitNumber(fib(++q))
    }
    return getNumber(fib(q), n - preNumb - 1)
}