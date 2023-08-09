package utils

import java.io.File

object GlobalExceptionCatch {
    init {
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            writeFileString(File("${System.currentTimeMillis()}-$e.log"), e.stackTraceToString())
        }
    }
}