package spamFilter

class User(val name: String) {
    val timeList = mutableListOf<Int>()
}

fun toTime(time: String): Int {
    val elemTime = time.split(':')
    val hours = elemTime[0].toInt()
    val minutes = elemTime[1].toInt()

    if (hours !in 0..23)
        throw IllegalArgumentException("Wrong hour: $hours")

    if (minutes !in 0..59)
        throw IllegalArgumentException("Wrong minutes: $minutes")

    return hours * 60 + minutes
}

fun textSplitter(text: String): List<String> {
    if (text.isEmpty())
        throw IllegalArgumentException("Empty text")

    val line = Regex("""[\d\w]+ \d{1,2}:\d{2}""")
    if (!text.matches(Regex("""($line\n)*$line""")))
        throw IllegalArgumentException("Wrong input format")

    val words = text.split(Regex("""[ \n]"""))
    for (i in 0 until words.size step 2) {
        if (!words[i].matches(Regex("""[\w\d]+""")))
            throw IllegalArgumentException("Wrong format name:${words[i]}!")

        if (!words[i + 1].matches(Regex("""\d{1,2}:\d{2}""")))
            throw IllegalArgumentException("Wrong format time:${words[i + 1]}!")
    }

    return words
}

fun spamList(text: String): List<String> {
    val words = textSplitter(text)
    val users = mutableSetOf<User>()

    //Pushing
    for (i in 0..words.lastIndex step 2) {
        val findRes = users.find { it.name == words[i] }

        if (findRes == null) {
            val tmpUser = User(words[i])
            tmpUser.timeList.add(toTime(words[i + 1]))
            users.add(tmpUser)
        }
        else
            findRes.timeList.add(toTime(words[i + 1]))
    }
    //!Pushing

    val spamList = mutableSetOf<String>()

    for (user in users) {
        user.timeList.sort()

        for (i in 0 until user.timeList.lastIndex) {
            if (Math.abs(user.timeList[i] - user.timeList[i + 1]) < 2) {
                spamList.add(user.name)
                break
            }
        }
    }

    return spamList.toList()
}
