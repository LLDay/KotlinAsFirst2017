package SpamFilter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Tests {
    @Test
    fun myFun() {
        assertFails { myFun("") }
        assertFails { myFun("Nick") }
        assertFails { myFun("10:10") }
        assertFails { myFun("Nick 10:3") }
        assertFails { myFun("N.ick 10:10") }
        assertFails { myFun("Nick 27:10") }
        assertFails { myFun("Nick 23:60") }
        assertFails { myFun("Nick\n10:10") }
        assertFails { myFun("Nick1 10:10 Nick2 20:20") }

        assertEquals( myFun("Nick 11:11"), emptyList())
        assertEquals( myFun("Nick 3:05"), emptyList())
        assertEquals( myFun("Nick 10:10\nNick 11:10"), emptyList())
        assertEquals( myFun("Nick 10:10\nNick 10:11"), listOf("Nick"))
        assertEquals( myFun("Nick1 10:10\nNick2 10:10\nNick3 10:10"), emptyList())
        assertEquals( myFun("Nick1 10:10\nNick2 3:18\nNick1 10:11"), listOf("Nick1"))
        assertEquals( myFun("Nick1 10:10\nNick2 3:18\nNick3 10:11\nNick2 3:17\nNick1 10:11").toSet(), setOf("Nick1", "Nick2"))
    }
}