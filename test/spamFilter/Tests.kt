package spamFilter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Tests {
    @Test
    fun myFun() {
        assertFails { spamList("") }
        assertFails { spamList("Nick") }
        assertFails { spamList("10:10") }
        assertFails { spamList("Nick 10:3") }
        assertFails { spamList("N.ick 10:10") }
        assertFails { spamList("Nick 27:10") }
        assertFails { spamList("Nick 23:60") }
        assertFails { spamList("Nick\n10:10") }
        assertFails { spamList("Nick1 10:10 Nick2 20:20") }

        assertEquals( spamList("Nick 11:11"), emptyList())
        assertEquals( spamList("Nick 3:05"), emptyList())
        assertEquals( spamList("Nick 10:10\nNick 11:10"), emptyList())
        assertEquals( spamList("Nick 10:10\nNick 10:11"), listOf("Nick"))
        assertEquals( spamList("Nick1 10:10\nNick2 10:10\nNick3 10:10"), emptyList())
        assertEquals( spamList("Nick1 10:10\nNick2 3:18\nNick1 10:11"), listOf("Nick1"))
        assertEquals( spamList("Nick1 10:10\nNick2 3:18\nNick3 10:11\nNick2 3:17\nNick1 10:11").toSet(), setOf("Nick1", "Nick2"))
    }
}