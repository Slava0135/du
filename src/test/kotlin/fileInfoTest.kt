import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class fileInfoTest() {

    @Test
    fun round() {
        assertEquals(round(2048, 1024), 2.0 to 1)
        assertEquals(round(1023, 1024), 1023.0 to 0)
        assertEquals(round(1023, 1000), 1.023 to 1)
        assertEquals(round(2_000_000_000, 1000), 2.0 to 3)
        assertEquals(round(2_000_000_000_000, 1000), 2000.0 to 3)
    }

    @Test
    fun getFilesInfo() {
        assertEquals(getFilesInfo(arrayOf("src/test/resources/sizeTest")).first().size, 23L)
        assertEquals(getFilesInfo(arrayOf("src/test/resources/sizeTest/sample1")).first().size, 2L)
        assertEquals(getFilesInfo(arrayOf("src/test/resources/sizeTest/sample2")).first().size, 21L)
        assertEquals(
            getFilesInfo(arrayOf("src/test/resources/sizeTest")).first().size,
            getFilesInfo(arrayOf("src/test/resources")).first().size)
    }
}
