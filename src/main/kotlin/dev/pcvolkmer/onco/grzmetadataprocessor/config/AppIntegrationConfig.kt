package dev.pcvolkmer.onco.grzmetadataprocessor.config

import dev.pcvolkmer.onco.grzmetadataprocessor.data.File
import dev.pcvolkmer.onco.grzmetadataprocessor.data.FileRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.data.FileType
import org.apache.tomcat.util.buf.HexUtils
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.Pollers
import org.springframework.integration.file.dsl.Files
import org.springframework.util.Assert
import java.nio.file.Path
import java.security.DigestInputStream
import java.security.MessageDigest
import java.time.Duration
import kotlin.io.path.*
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@ConfigurationProperties(AppSourceFsProperties.NAME)
data class AppSourceFsProperties(
    val directory: Path? = null,
    val pollDelay: Duration = 1.minutes.toJavaDuration(),
) {
    companion object {
        const val NAME = "app.source.fs"
    }
}

@Configuration
@EnableConfigurationProperties(AppSourceFsProperties::class)
class AppIntegrationConfig {

    @Bean
    @ConditionalOnProperty(
        name = ["app.source.fs.directory"]
    )
    fun fileInputFlow(
        applicationFsProperties: AppSourceFsProperties,
        fileRepository: FileRepository
    ): IntegrationFlow {
        val sourceDirectory = applicationFsProperties.directory
        Assert.state(null != sourceDirectory && sourceDirectory.isDirectory()) {
            "Property 'app.source.fs.active' is 'true' but source directory is not available"
        }
        return IntegrationFlow
            .from(
                Files.inboundAdapter(sourceDirectory!!.toFile()).useWatchService(true)
            )
            .log()
            .handle { msg ->
                val path = Path(msg.payload.toString())
                val relativePath = applicationFsProperties.directory.relativize(Path(msg.payload.toString())).pathString
                fileRepository.findByFilePath(relativePath).ifPresentOrElse({
                    // File already present
                }, {
                    fileRepository.save(
                        File(
                            filePath = relativePath,
                            labDataId = null,
                            fileChecksum = calcFileChecksum(path),
                            fileSizeInBytes = path.fileSize(),
                            fileType = getFileType(path),
                        )
                    )
                })

            }
            .get()
    }

    private fun calcFileChecksum(path: Path): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digestInputStream = DigestInputStream(path.inputStream(), messageDigest)
        digestInputStream.readAllBytes()
        return HexUtils.toHexString(messageDigest.digest())
    }

    private fun getFileType(path: Path): FileType? {
        return if (path.toString().lowercase().endsWith(".fastq.gz")) {
            FileType.FASTQ
        } else if (path.toString().lowercase().endsWith(".bed")) {
            FileType.BED
        } else if (path.toString().lowercase().endsWith(".bam")) {
            FileType.BAM
        } else if (path.toString().lowercase().endsWith(".vcf")) {
            FileType.VCF
        } else {
            null
        }
    }

}
