package spamFilter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class Tests {
    @Test
    fun myFun() {
        try {
            spamList("")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("Nick 10: 10")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("10:10")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("Nick 10:3")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("N.ick 10:10")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("Nick 27:10")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("Nick 23:60")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("Nick\n10:10")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        try {
            spamList("Nick1 10:10 Nick2 20:20")
            assert(false)
        }
        catch(e: IllegalArgumentException) { assert(true) }
        catch(e: Throwable) { assert(false) }

        assertEquals(spamList("Nick 11:11"), listOf<String>())
        assertEquals(spamList("Nick 3:05"), listOf<String>())
        assertEquals(spamList("Nick 10:10\nNick 11:10"), listOf<String>())
        assertEquals(spamList("Nick 10:10\nNick 10:11"), listOf("Nick"))
        assertEquals(spamList("Nick1 10:10\nNick2 3:18\nNick1 10:11"), listOf("Nick1"))

        val spam = spamList("Nick1 10:10\nNick2 3:18\nNick3 10:11\nNick2 3:17\nNick1 10:11")
        assertTrue(spam.size == 2 && spam.toSet() == setOf("Nick1", "Nick2"))
    }
}