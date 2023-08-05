import utils.ConfigUtil
import utils.getResourceByFileName
import java.io.File
import java.util.*

object AppConfig {
    var language: String = Locale.getDefault().language
    var languageMap = mapOf<String, String>()

    var appConfig = mapOf<String, String>()

    init {
        initConfig()
        initLanguage()
    }

    private fun initConfig() {
        appConfig = ConfigUtil.parseConfigFile(
            getResourceByFileName(File("app.config"))!!.toFile()
        )
    }

    private fun initLanguage() {
        languageMap = ConfigUtil.parseConfigFile(
            getResourceByFileName(File("/languages/$language.lang"))
                ?.toFile() ?: getResourceByFileName(File("/languages/en.lang"))!!.toFile()
        )
    }
}