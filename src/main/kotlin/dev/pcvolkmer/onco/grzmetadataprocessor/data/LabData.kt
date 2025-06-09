package dev.pcvolkmer.onco.grzmetadataprocessor.data

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

@Table("tbl_lab_data")
data class LabData(
    @Id val id: Long? = null,
    val donorId: Long?,
    val einsendenummer: String = "",
    val labDataName: String = "",
    val sampleDate: String = "",
    val sampleConservation: SampleConservation? = null,
    val sequenceType: SequenceType? = null,
    val sequenceSubtype: SequenceSubtype? = null,
    val fragmentationMethod: FragmentationMethod? = null,
    val profile: Long? = null,
    val libraryType: LibraryType? = null,
    val libraryPrepKit: String = "",
    val libraryPrepKitManufacturer: String = "",
    val sequencerModel: String = "",
    val sequencerManufacturer: String = "",
    val kitName: String = "",
    val kitManufacturer: String = "",
    val enrichmentKit: String = "",
    val enrichmentKitManufacturer: EnrichmentKitManufacturer? = null,
    val sequencingLayout: SequencingLayout? = null,
    val tumorCellCount: Int = 0,
    val tumorCellCountMethod: TumorCellCountMethod? = null,
    val bioinformaticsPipelineName: String = "",
    val bioinformaticsPipelineVersion: String = "",
    val referenceGenome: ReferenceGenome? = null,
) {
    @Transient
    var profileData: LabDataProfile? = null
}

enum class SampleConservation(val value: String) {
    FRESH_TISSUE("fresh-tissue"),
    CRYO_FROZEN("cryo-frozen"),
    FFPE("ffpe"),
    OTHER("other"),
    UNKNOWN("unknown")
}

enum class SequenceType(val value: String) {
    DNA("dna"),
    RNA("rna")
}

enum class SequenceSubtype(val value: String) {
    GERMLINE("germline"),
    SOMATIC("somatic"),
    OTHER("other"),
    UNKNOWN("unknown")
}

enum class FragmentationMethod(val value: String) {
    SONICATION("sonication"),
    ENZYMATIC("enzymatic"),
    NONE("none"),
    OTHER("other"),
    UNKNOWN("unknown")
}

enum class LibraryType(val value: String) {
    PANEL("panel"),
    PANEL_LR("panel_lr"),
    WES("wes"),
    WES_LR("wes_lr"),
    WGS("wgs"),
    WGS_LR("wgs_lr"),
    WXS("wxs"),
    WXS_LR("wxs_lr"),
    OTHER("other"),
    UNKNOWN("unknown")
}

enum class EnrichmentKitManufacturer(val value: String) {
    ILLUMINA("Illumina"),
    AGILENT("Agilent"),
    TWIST("Twist"),
    NEB("NEB"),
    OTHER("other"),
    UNKNOWN("unknown"),
    NONE("none")
}

enum class SequencingLayout(val value: String) {
    SINGLE_END("single-end"),
    PAIRED_END("paired-end"),
    REVERSE("reverse"),
    OTHER("other"),
}

enum class TumorCellCountMethod(val value: String) {
    PATHOLOGY("pathology"),
    BIOINFORMATICS("bioinformatics"),
    OTHER("other"),
    UNKNOWN("unknown")
}

enum class ReferenceGenome(val value: String) {
    GRCH37("GRCh37"),
    GRCH38("GRCh38"),
}

interface LabDataRepository : CrudRepository<LabData, Long> {
    fun getById(id: Long): LabData?
    fun findByDonorId(donorId: Long): MutableList<LabData>
    fun countLabDataByDonorIdIsNull(): Long
    fun findByEinsendenummer(einsendenummer: String): LabData
}
