package info.adavis.qualitychecks.tasks

import info.adavis.qualitychecks.QualityChecksPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class WriteConfigFileTask : DefaultTask() {

    @OutputFile
    @Optional
    var configFile: File? = null

    @Input
    @Optional
    var fileName: String? = null

    init {
        group = QualityChecksPlugin.VERIFICATION_GROUP

        onlyIf {
            configFile != null
        }
    }

    @TaskAction
    fun writeConfigFile() {
        description = "Write config file for quality checks task"

        configFile?.let {
            logger.info("Copying the file contents from $fileName")
            copyConfigFile(fileName, configFile)
        }
    }

    companion object {

        fun copyConfigFile(fileName: String?, configFile: File?) {
            ClassLoader.getSystemResourceAsStream(fileName).use { inputStream ->
                configFile?.outputStream()?.use { inputStream?.copyTo(it) }
            }
        }
    }
}
