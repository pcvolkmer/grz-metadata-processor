package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataProfile
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/labdataprofiles")
class LabDataProfileController(
    private val repository: LabDataProfileRepository
) {

    @GetMapping
    fun getAllLabDataProfiles(model: Model): String {
        model.addAttribute("labdataprofiles", repository.findAll().sortedByDescending { it.id })
        return "labdataprofiles"
    }

    @PostMapping
    fun postLabDataProfile(model: Model): String {
        repository.save(LabDataProfile(profileName = "Neues Sequenzierprofil"))
        model.addAttribute("labdataprofiles", repository.findAll().sortedByDescending { it.id })
        return "labdataprofiles"
    }

    @GetMapping(path = ["{labdataProfileId}"])
    fun getLabDataProfile(@PathVariable labdataProfileId: Long, model: Model): String {
        model.addAttribute("profile", repository.findByIdOrNull(labdataProfileId))
        return "labdataprofile"
    }

    @PutMapping(path = ["{labdataProfileId}"])
    fun putLabDataProfile(@PathVariable labdataProfileId: Long, labDataProfile: LabDataProfile, model: Model): String {
        repository.save(labDataProfile)
        model.addAttribute("labdataprofiles", repository.findAll().sortedByDescending { it.id })
        return "labdataprofiles"
    }

    @DeleteMapping(path = ["{labdataProfileId}"])
    fun deleteLabDataProfile(@PathVariable labdataProfileId: Long, model: Model): String {
        repository.deleteById(labdataProfileId)
        model.addAttribute("labdataprofiles", repository.findAll().sortedByDescending { it.id })
        return "labdataprofiles"
    }

}
