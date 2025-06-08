package dev.pcvolkmer.onco.grzmetadataprocessor.data

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

@Table("tbl_donor")
data class Donor(
    @Id val id: Long? = null,
    val caseId: Long?,
    val donorPseudonym: String = "",
    val gender: Gender? = null,
    val relation: Relation? = null,
)

enum class Gender(val value: String) {
    MALE("male"),
    FEMALE("female"),
    OTHER("other"),
    UNKNOWN("unknown")
}

enum class Relation(val value: String) {
    MOTHER("mother"),
    FATHER("father"),
    BROTHER("brother"),
    SISTER("sister"),
    CHILD("child"),
    INDEX("index"),
    OTHER("other")
}

interface DonorRepository : CrudRepository<Donor, Long> {
    fun findByCaseId(caseId: Long): MutableList<Donor>
}
