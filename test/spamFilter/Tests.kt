package spamFilter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class Tests {
    @Test
    fun myFun() {
        try {
            spamList("")
            spamList("Nick")
            spamList("10:10")
            spamList("Nick 10:3")
            spamList("N.ick 10:10")
            spamList("Nick 27:10")
            spamList("Nick 23:60")
            spamList("Nick\n10:10")
            spamList("Nick1 10:10 Nick2 20:20")
        }
        catch(e: IllegalArgumentException) {
            assert(true)
        }
        catch(e: Throwable) {
            assert(false) {e.message.toString()}
        }

        assertEquals( spamList("Nick 11:11"), listOf<String>())
        assertEquals( spamList("Nick 3:05"), listOf<String>())
        assertEquals( spamList("Nick 10:10\nNick 11:10"), listOf<String>())
        assertEquals( spamList("Nick 10:10\nNick 10:11"), listOf("Nick"))
        assertEquals( spamList("Nick1 10:10\nNick2 3:18\nNick1 10:11"), listOf("Nick1"))
        assertEquals( spamList("Nick1 10:10\nNick2 3:18\nNick3 10:11\nNick2 3:17\nNick1 10:11").toSet(), setOf("Nick1", "Nick2"))
    }
}