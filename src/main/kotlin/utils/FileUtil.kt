package utils

import java.io.File

fun readFileString(file: File): String {
    return file.readText()
}

fun readFileByteArray(file: File): ByteArray {
    return file.readBytes()
}