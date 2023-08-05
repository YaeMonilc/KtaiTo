package utils

import AppConfig
import java.io.File

object ConfigUtil {
    fun parseConfigFile(file: File): Map<String, String> {
        return parseConfigString(readFileString(file))
    }

    fun parseConfigString(config: String): Map<String, String> {
        val map = mutableMapOf<String, String>()
        config.lines().forEach {
            val keyValue = it.split(" = ")
            if (keyValue.size > 1)
                map[keyValue[0]] = keyValue[1]
        }
        return map
    }
}

fun getLangText(id: String): String {
    return AppConfig.languageMap[id] ?: id
}

fun getConfig(id: String): String {
    return AppConfig.appConfig[id] ?: id
}