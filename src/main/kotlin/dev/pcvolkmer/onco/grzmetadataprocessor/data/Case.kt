package dev.pcvolkmer.onco.grzmetadataprocessor.data

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

@Table("tbl_case")
data class Case(
    @Id val id: Long? = null,
    var localCaseId: String = "",
    var coverageType: CoverageType = CoverageType.UNK,
    var submitterId: String = "",
    var genomicDataCenterId: String = "",
    var clinicalDataCenterId: String = "",
    var diseaseType: DiseaseType? = null,
    var genomicStudyType: GenomicStudyType? = null,
    var genomicStudySubtype: GenomicStudySubtype? = null,
    var labName: String = "",
)

enum class CoverageType {
    GKV,
    PKV,
    BG,
    SEL,
    SOZ,
    GPV,
    PPV,
    BEI,
    SKT,
    UNK
}

enum class DiseaseType(value: String) {
    ONCOLOGICAL("oncological"),
    RARE("rare"),
    HEREDITARY("hereditary"),
}

enum class GenomicStudyType(value: String) {
    SINGLE("single"),
    DUO("duo"),
    TRIO("trio"),
}

enum class GenomicStudySubtype(value: String) {
    TUMOR_ONLY("tumor-only"),
    TUMOR_GERMLINE("tumor+germline"),
    GERMLINE_ONLY("germline-only"),
}

interface CaseRepository : CrudRepository<Case, Long>
