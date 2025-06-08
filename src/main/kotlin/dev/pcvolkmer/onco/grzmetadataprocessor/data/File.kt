package dev.pcvolkmer.onco.grzmetadataprocessor.data

import org.apache.tomcat.util.buf.HexUtils
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.nio.file.Path
import java.security.DigestInputStream
import java.security.MessageDigest
import java.util.*
import kotlin.io.path.fileSize
import kotlin.io.path.inputStream

@Table("tbl_file")
data class File(
    @Id val id: Long? = null,
    val labDataId: Long?,
    val filePath: String? = null,
    val fileType: FileType? = null,
    var fileChecksum: String? = null,
    var fileSizeInBytes: Long? = null,
) {
    init {
        if (fileChecksum.isNullOrBlank()) {
            fileChecksum = calcFileChecksum()
        }

        if (fileSizeInBytes?.or(0)!! < 1) {
            fileSizeInBytes = calcFileSize()
        }
    }

    fun calcFileChecksum(): String {
        if (filePath == null) {
            return ""
        }
        val path = Path.of(filePath)
        val messageDigest = MessageDigest.getInstance("SHA-256")

        val digestInputStream = DigestInputStream(path.inputStream(), messageDigest)
        digestInputStream.readAllBytes()
        return HexUtils.toHexString(messageDigest.digest())
    }

    fun calcFileSize(): Long {
        if (filePath == null) {
            return 0
        }
        return  Path.of(filePath).fileSize()
    }
}

enum class FileType(val value: String) {
    BAM("bam"),
    VCF("vcf"),
    BED("bed"),
    FASTQ("fastq")
}

interface FileRepository : CrudRepository<File, Long> {
    fun findByLabDataId(labDataId: Long): MutableList<File>
    fun findByLabDataIdIsNull(): List<File>
}
