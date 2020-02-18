package du

import java.io.File
import kotlin.system.exitProcess

class SizeManager(private val base: Int, private val humanLike: Boolean, private val total: Boolean, private val paths: List<String>) {

    private val units = listOf("B", "KB", "MB", "GB")

    private fun getFileInfo(): List<FileInfo> {
        val result = mutableListOf<FileInfo>()
        for (name in paths) {
            val file = File(name)
            if (file.exists()) {
                if (file.isDirectory) {
                    var sum = file.length()
                    for (subFile in file.walk()) {
                        sum += subFile.length()
                    }
                    result.add(FileInfo(name, sum))
                } else result.add(FileInfo(name, file.length()))
            } else result.add(FileInfo(name, null))
        }
        return result
    }
    private val files = getFileInfo()

    fun getInfo() {
        if (total) {
            if (files.all { it.size != null }) {
                var sum = 0L
                for (file in files) {
                    sum += file.size!!
                }
                if (humanLike) {
                    val (value, unit) = round(sum)
                    println("Total size of files: ${"%.2f".format(value)} ${units[unit]}")
                } else {
                    println("Total size of files: ${sum / base}")
                }
            } else {
                for (file in files) {
                    if (file.size == null) {
                        println("File ${file.name} doesn't exist!")
                    }
                    exitProcess(1)
                }
            }
        }
        else {
            for (file in files) {
                if (file.size == null) {
                    println("${file.name} doesn't exist!")
                } else {
                    if (humanLike) {
                        val (value, unit) = round(file.size)
                        println("${file.name} size is ${"%.2f".format(value)} ${units[unit]}")
                    } else {
                        println("${file.name} size is ${file.size / base}")
                    }
                }
            }
        }
    }

    private fun round(size: Long): Pair<Double, Int> {
        var value = size.toDouble()
        var unit = 0
        while (unit < units.size - 1 && value > base) {
            value /= base
            unit++
        }
        return value to unit
    }
}

class FileInfo(val name: String, val size: Long?)