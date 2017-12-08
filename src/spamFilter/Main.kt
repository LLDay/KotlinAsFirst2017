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

    val lineCheck = Regex("""^[\d\w]+ \d{1,2}:\d{2}$""")
    val lines = text.split('\n')
    val words = mutableListOf<String>()

    for (line in lines)
        if (!line.matches(lineCheck))
            throw IllegalArgumentException("Wrong input format: \"$line\"")
        else words.addAll(line.split(' '))

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

    val spamList = mutableListOf<String>()

    for (user in users) {
        user.timeList.sort()

        for (i in 0 until user.timeList.lastIndex) {
            if (user.timeList[i + 1] - user.timeList[i] < 2) {
                spamList.add(user.name)
                break
            }
        }
    }

    return spamList
}
