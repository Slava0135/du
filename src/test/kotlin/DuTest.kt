import Du.main
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DuTest {

    @Test
    fun doMain() {
        fun test(line: String, h: Boolean, c: Boolean, si: Boolean, fileNames: Array<String>) {
            val du = Du()
            du.doMain(line.split(" ").toTypedArray())
            assertEquals(du.human, h)
            assertEquals(du.count, c)
            assertEquals(du.base, si)
            if (du.fileNames != null) {
                assert(du.fileNames!!.contentEquals(fileNames))
            } else {
                assert(fileNames.isEmpty())
            }
        }
        test("-h hi", true, false, false, arrayOf("hi"))
        test("-h", true, false, false, emptyArray())
        test("-h -c --si", true, true, true, emptyArray())
        test("", false, false, false, arrayOf(""))
        test("-h -c --si hello world", true, true, true, arrayOf("hello", "world"))
        test("---key", false, false, false, emptyArray())
    }
}