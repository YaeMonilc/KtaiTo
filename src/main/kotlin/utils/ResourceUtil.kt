package utils

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private val files: List<Path> = Files.walk(
    Paths.get(
        Paths.get("").toAbsolutePath().toString(),
        "/src/main/resources")
).toList()

fun getResourceByFileName(file: File): Path? {
    return files.firstOrNull { (it.toString()).contains(file.path) }
}

fun getResourceBySuffix(suffix: String): List<Path> {
    return files.filter { it.toString().endsWith(suffix) }.toList()
}