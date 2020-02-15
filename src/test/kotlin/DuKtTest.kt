package du

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Paths

class DuKtTest {

    @Test
    fun split() {
        assertEquals(listOf("-h", """"file 1"""", """"file 2""""), split("""-h "file 1" "file 2""""))
        assertEquals(listOf("-h", "-c", "--si", """"file 1"""", """"file 2""""), split("""-h -c --si "file 1" "file 2""""))
    }

    @Test
    fun match() {
        assertTrue(match("""-h -c --si "file 1" "file 2" "file 3""""))
        assertTrue(match(""""file 1""""))
        assertFalse(match(""))
        assertFalse(match("-h"))
        assertFalse(match("-c -h -si \"file 1\""))
        assertFalse(match("-h -c --si file1 file2 file3"))
    }

    @Test
    fun deleteDuplicates() {
        assertEquals(listOf("prefix"), deleteDuplicates(listOf("prefix", "prefix/full")))
        assertEquals(listOf("prefix"), deleteDuplicates(listOf("prefix", "prefix/full", "prefix/half")))
        assertEquals(listOf("prefix/first", "prefix/third"), deleteDuplicates(listOf("prefix/first", "prefix/first/second", "prefix/third")))
    }

    @Test
    fun getEveryFileSize() {
        val root = Paths.get("").toAbsolutePath().toString()
        val testRoot = "$root\\src\\test\\resources\\sizeTest"
        assertEquals(2, getEveryFileSize(listOf("$testRoot\\sample1")).first())
        assertNull(getEveryFileSize(listOf(testRoot + "lol")).first())
        assertEquals(listOf(2L, 21L), getEveryFileSize(listOf("$testRoot\\sample1", "$testRoot\\sample2")))
        assertEquals(listOf(23L), getEveryFileSize(listOf(testRoot)))
    }

    @Test
    fun round() {
        assertEquals(Pair(2.0, 1), round(1024, 2048))
        assertEquals(Pair(2.0, 1), round(1000, 2000))
        assertEquals(Pair(2.0, 2), round(1000, 2_000_000))
        assertEquals(Pair(2.0, 3), round(1000, 2_000_000_000))
        assertEquals(Pair(2000.0, 3), round(1000, 2_000_000_000_000))
        assertEquals(Pair(1024.0, 3), round(1024, 1024L * 1024 * 1024 * 1024))
    }
}