package dev.pcvolkmer.onco.grzmetadataprocessor.data

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository

@Table("tbl_lab_data_profile")
data class LabDataProfile(
    @Id val id: Long? = null,
    val profileName: String = "",
    val libraryType: LibraryType? = null,
    val libraryPrepKit: String = "",
    val libraryPrepKitManufacturer: String = "",
    val sequencerModel: String = "",
    val sequencerManufacturer: String = "",
    val kitName: String = "",
    val kitManufacturer: String = "",
    val enrichmentKit: String = "",
    val enrichmentKitManufacturer: EnrichmentKitManufacturer? = null
)

interface LabDataProfileRepository : CrudRepository<LabDataProfile, Long>
