import java.io.File

val units = listOf("B", "KB", "MB", "GB")

fun main() {
    println("line: du [-h] [-c] [--si] \"file1\" \"file2\" \"file3\" ...")
    print("line: du ")
    val inputLine = readLine()
    return if (inputLine != null && match(inputLine)) {
        analyze(split(inputLine))
    } else {
        println("Invalid line format!")
    }
}

fun split(inputLine: String) = inputLine.split(Regex("""((?<=") (?=")| (?=[-"]))"""))

fun match(inputLine: String) = inputLine.matches(Regex("""(-h )?(-c )?(--si )?(".+" )*(".+")"""))

fun analyze(data: List<String>) {
    val args = data.filter { it.startsWith("-") }
    val fileList = data.filter { it.startsWith("\"") }.map { name -> name.filter { it != '"' } }

    val fileInfo = if ("-c" in args)
        getEveryFileSize(deleteDuplicates(fileList))
    else getEveryFileSize(fileList)

    val result = mutableListOf<Long?>()
    //summary size
    if ("-c" in args) {
        if (fileInfo.any { it == null } ) {
            for (error in fileInfo.filter { it == null }.indices) {
                println("${fileList[error]} doesn't exist!")
            }
            return
        } else {
            var sum: Long = 0
            for (size in fileInfo) {
                sum += size!!
            }
            result.add(sum)
        }
    } else {
        result.addAll(fileInfo)
    }
    val base = if ("--si" in args) 1000 else 1024
    //unit converting
    if ("-h" in args) {
        if ("-c" in args) {
            val (value, unit) = round(base, result[0]!!)
            println("Total size of files: ${"%.2f".format(value)} ${units[unit]}")
        } else {
            for (fileN in result.indices) {
                val size = result[fileN]
                if (size == null) {
                    println("${fileList[fileN]} doesn't exist!")
                } else {
                    val (value, unit) = round(base, result[fileN]!!)
                    println("${fileList[fileN]} size is ${"%.2f".format(value)} ${units[unit]}")
                }
            }
        }
    } else {
        if ("-c" in args) {
            println("Total size of files: ${result[0]!! / base}")
        } else {
            for (fileN in result.indices) {
                val size = result[fileN]
                if (size == null) {
                    println("${fileList[fileN]} doesn't exist!")
                } else {
                    println("${fileList[fileN]} size is ${size / base}")
                }
            }
        }
    }
}

fun deleteDuplicates(fileList: List<String>) = fileList.filter { file -> fileList.all{ file.startsWith(it) } }

fun getEveryFileSize(fileList: List<String>): List<Long?> {
    val result = mutableListOf<Long?>()
    for (fileName in fileList) {
        val file = File(fileName)
        if (file.exists()) {
            if (file.isDirectory) {
                var sum = file.length()
                for (subFile in file.walk()) {
                    sum += subFile.length()
                }
                result.add(sum)
            } else result.add(file.length())
        } else result.add(null)
    }
    return result
}

fun round(base: Int, num: Long): Pair<Float, Int> {
    var number = num.toFloat()
    var count = 0
    while (count < units.size - 1 && number > base) {
        number /= base
        count++
    }
    return Pair(number, count)
}