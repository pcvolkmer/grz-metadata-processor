package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabData
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataProfileRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/donors/{donorId}/labdatas")
class LabDataController(
    private val labDataRepository: LabDataRepository,
    private val labDataProfileRepository: LabDataProfileRepository
) {

    @GetMapping
    fun getAllLabData(@PathVariable donorId: Long, model: Model): String {
        model.addAttribute("labdatas", allLabDatasByDonorId(donorId))
        model.addAttribute("labdataprofiles", labDataProfileRepository.findAll())
        return "labdatas"
    }

    @PostMapping
    fun postLabData(@PathVariable donorId: Long, model: Model): String {
        labDataRepository.save(LabData(donorId = donorId))
        model.addAttribute("labdatas", allLabDatasByDonorId(donorId))
        model.addAttribute("labdataprofiles", labDataProfileRepository.findAll())
        return "labdatas"
    }

    @PutMapping(path = ["{labdataId}"])
    fun putLabData(@PathVariable donorId: Long, labData: LabData, model: Model): String {
        labDataRepository.save(labData)
        model.addAttribute("labdatas", allLabDatasByDonorId(donorId))
        model.addAttribute("labdataprofiles", labDataProfileRepository.findAll())
        return "labdatas"
    }

    @DeleteMapping(path = ["{labdataId}"])
    fun deleteLabData(@PathVariable donorId: Long, @PathVariable labdataId: Long, model: Model): String {
        labDataRepository.deleteById(labdataId)
        model.addAttribute("labdatas", allLabDatasByDonorId(donorId))
        model.addAttribute("labdataprofiles", labDataProfileRepository.findAll())
        return "labdatas"
    }

    private fun allLabDatasByDonorId(donorId: Long): List<LabData> {
        return labDataRepository.findByDonorId(donorId)
            .map { it.apply { labDataProfileRepository.findById(this.profile ?: 0).ifPresent { profileData = it } } }
    }

}
