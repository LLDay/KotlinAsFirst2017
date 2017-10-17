@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

import lesson4.task1.roman

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

//високосный ли год
fun isLeapYear(year: Int): Boolean =
    when {
        year % 400 == 0 -> true
        year % 100 == 0 -> false
        year % 4   == 0 -> true
        else -> false
    }


//Проверка на существование даты
fun correctDate(day: Int, month: Int, year: Int): Boolean {
    if (month !in 1..12) return false
    if (month == 2 && day in 1..28) return true

    if (month == 2 && day == 29 && isLeapYear(year))
        return true

    if (day in 1..when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        else -> 30
    }) return true

    return false
}

fun stringToMonth(month: String): Int =
        when (month) {
            "января"   -> 1
            "февраля"  -> 2
            "марта"    -> 3
            "апреля"   -> 4
            "мая"      -> 5
            "июня"     -> 6
            "июля"     -> 7
            "августа"  -> 8
            "сентября" -> 9
            "октября"  -> 10
            "ноября"   -> 11
            "декабря"  -> 12
            else -> throw IllegalArgumentException("bad month $month")
        }

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {
    val list = str.split(" ")
    if (list.size != 3) return ""

    return try {
        val day = list[0].toInt()
        val month = stringToMonth(list[1])
        val year = list[2].toInt()

        if (correctDate(day, month, year))
            "%02d.%02d.%d".format(day, month, year)
        else ""
    } catch (e: Exception) {
        ""
    }
}

fun intToMonth(month: String): String =
        when (month.toInt()) {
            1  -> "января"
            2  -> "февраля"
            3  -> "марта"
            4  -> "апреля"
            5  -> "мая"
            6  -> "июня"
            7  -> "июля"
            8  -> "августа"
            9  -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> throw IllegalArgumentException("bad month $month")
        }

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val list = digital.split(".")
    if (list.size != 3) return ""

    return try {
        val day = list[0].toInt()
        val month = intToMonth(list[1])
        val year = list[2].toInt()

        if (correctDate(day, list[1].toInt(), year))
            "$day $month $year"
        else ""
    } catch (e: Exception) {
        ""
    }
}


/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (!Regex("""^\+?[0-9 ()\-]+$""").matches(phone)) return ""
    return phone.replace(Regex("""[() \-]+"""), "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val jList = jumps.split(Regex(" +"))
    val checkR = Regex("""^\d+|%|-$""")

    var max = -1

    for (el in jList) {
        if (!el.matches(checkR))
            return -1

        if (el.matches(Regex("""^\d+$"""))) {
            val jump = el.toInt()
            if (jump > max)
                max = jump
        }
    }
    return max
}


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val jList = jumps.split(Regex(" +"))
    val checkR = Regex("^\\d+[+\\-%]+$")
    var max = -1

    for (i in  0 until jList.lastIndex step 2) {
        val attempts = Pair(jList[i], jList[i + 1])

        if (!(attempts.first + attempts.second).matches(checkR))
            return -1
        if (attempts.second.indexOf('+') != -1)
            max = Math.max(max, attempts.first.toInt())
    }

    return max
}


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (!expression.matches(Regex("""^\d+( +[+\-] +(\d+))*$""")))
        throw IllegalArgumentException("Wrong format")

    val plus = Regex("""(^\d+)|(\+ +\d+)""").findAll(expression)
    val minus = Regex("""- +\d+""").findAll(expression)
    var result = 0

    for (el in plus) {
        val tmp = Regex("""\d+""").find(el.value)
        if (tmp != null)
            result += tmp.value.toInt()
        else throw IllegalArgumentException("")
    }

    for (el in minus) {
        val tmp = Regex("""\d+""").find(el.value)
        if (tmp != null)
            result -= tmp.value.toInt()
        else throw IllegalArgumentException("")
    }

    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val words = str.split(' ')

    var currentIndex = 0

    for (i in 0 until words.lastIndex) {
        if (words[i].toLowerCase() == words[i + 1].toLowerCase())
            return currentIndex
        currentIndex += words[i].length + 1
    }

    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    val tmpReg = Regex("""[^ ]+ \d+(.?\d*)""") //один товар с ценой
    val checkFormat = Regex("""^($tmpReg; )*($tmpReg)$""")
    if (!description.matches(checkFormat))
        return ""

    var maxPrice = -1.0
    var product = ""
    val list = description.split("; ")

    for (i in 0..list.lastIndex) {
        val elem = list[i].split(' ')
        val nameProduct = elem[0]
        val currentPrice = elem[1].toDouble()

        if (currentPrice > maxPrice) {
            maxPrice = currentPrice
            product = nameProduct
        }
    }
    return product
}

fun getValueR(ch: Char) =
        when (ch) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> throw IllegalArgumentException("")
        }


fun romanHelpRev(str: String): Int {
    //Результат
    var res = 0

    //Буфер для вычитания или прибавления
    var buf = getValueR(str[0])

    //Хранение предыдущего значения
    var last = buf


    for (i in 1..str.lastIndex) {
        val ch = str[i]

        //Хранение текущего значения
        val now = getValueR(ch)

        when {
            last >= now -> buf += now            //XI, XX
            buf > now   -> buf += now - 2 * last //XIV
            else -> {                            //IX
                res += now - buf
                buf = 0
            }
        }

        last = now
    }
    res += buf

    return res
}


/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    return try {
        val value = romanHelpRev(roman)
        if (roman(value) == roman) value
        else -1
    }
    catch (e: Exception) {
        -1
    }
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */


class Convey(val cells: Int, val limit: Int, val commands: String) {
    init{
        checkCommands()
    }

    fun getResult() : List<Int> {
        while (limitNumber++ < limit && indexCommand < commands.length) {
            val command = commands[indexCommand]

            when (command) {
                '>' -> this.toRight()
                '<' -> this.toLeft()
                '+' -> this.plus()
                '-' -> this.minus()
                '[' -> {
                    if (this.isNull()) indexCommand = this.moveRight()
                }
                ']' -> {
                    if (!this.isNull()) indexCommand = this.moveLeft()
                }
            }
            indexCommand++
        }
        return list.toList()
    }

    private fun toRight() {
        if (++cursor >= cells)
            throw IllegalStateException("")
    }
    private fun toLeft() {
        if (--cursor < 0)
            throw IllegalStateException("")
    }

    private fun plus() {
        list[cursor]++
    }
    private fun minus() {
        list[cursor]--
    }

    private fun moveRight(): Int {
        var level = 1 //уровень скобок
        var i = indexCommand

        while (level != 0)
            when (commands[++i]) {
                '[' -> level++
                ']' -> level--
            }
        return i
    }
    private fun moveLeft(): Int {
        var level = 1 //уровень скобок
        var i = indexCommand

        while (level != 0)
            when (commands[--i]) {
                ']' -> level++
                '[' -> level--
            }

        return i
    }

    private fun isNull() = list[cursor] == 0
    private fun checkCommands() {
        var level = 0

        for (el in commands) {
            when (el) {
                '[' -> level++
                ']' -> level--
            }
            if (level < 0) throw IllegalArgumentException("")
        }

        if (level != 0) throw IllegalArgumentException("")

        if (commands.matches(Regex("""[^ <>+\-\[\]]""")))
            throw IllegalArgumentException("")
    }

    private val list = IntArray(cells)
    private var cursor = cells / 2

    private var limitNumber = 0
    private var indexCommand = 0
}


fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> =
        Convey(cells, limit, commands).getResult()

