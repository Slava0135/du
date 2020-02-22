import java.io.File

val units = listOf("B", "KB", "MB", "GB")
fun round(size: Long, base: Int): Pair<Double, Int> {
    var value = size.toDouble()
    var unit = 0
    while (unit < units.size - 1 && value > base) {
        value /= base
        unit++
    }
    return value to unit
}

data class FileInfo(val name: String, val size: Long?)

fun printInfo(base: Int, humanLike: Boolean, total: Boolean, paths: Array<String>) {
    val files = getFilesInfo(paths)
    if (total) {
        if (files.all { it.size != null }) {
            var sum = 0L
            for (file in files) {
                sum += file.size ?: 0
            }
            if (humanLike) {
                val (value, unit) = round(sum, base)
                println("Total size of files: ${"%.2f".format(value)} ${units[unit]}")
            } else {
                println("Total size of files: ${sum / base}")
            }
        } else {
            for (file in files) {
                if (file.size == null) {
                    println("ERROR: File ${file.name} doesn't exist!")
                }
            }
        }
    }
    else {
        for (file in files) {
            if (file.size == null) {
                println("ERROR: File ${file.name} doesn't exist!")
            } else {
                if (humanLike) {
                    val (value, unit) = round(file.size, base)
                    println("${file.name} size is ${"%.2f".format(value)} ${units[unit]}")
                } else {
                    println("${file.name} size is ${file.size / base}")
                }
            }
        }
    }
}

fun getFilesInfo(paths: Array<String>): List<FileInfo> {
    val result = mutableListOf<FileInfo>()
    for (name in paths) {
        val file = File(name)
        if (file.exists()) {
            if (file.isDirectory) {
                result.add(FileInfo(name, file.walk()
                    .fold(0L) { prevResult, it -> prevResult + if (!it.isDirectory) it.length() else 0 }))
            } else result.add(FileInfo(name, file.length()))
        } else result.add(FileInfo(name, null))
    }
    return result
}