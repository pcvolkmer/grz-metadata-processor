package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabData
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/donors/{donorId}/labdatas")
class LabDataController(
    private val repository: LabDataRepository
) {

    @GetMapping
    fun getAllLabData(@PathVariable donorId: Long, model: Model): String {
        model.addAttribute("labdatas", repository.findByDonorId(donorId))
        return "labdatas"
    }

    @PostMapping
    fun postLabData(@PathVariable donorId: Long, model: Model): String {
        repository.save(LabData(donorId = donorId))
        model.addAttribute("labdatas", repository.findByDonorId(donorId))
        return "labdatas"
    }

    @PutMapping(path = ["{labdataId}"])
    fun putLabData(@PathVariable donorId: Long, labData: LabData, model: Model): String {
        repository.save(labData)
        model.addAttribute("labdatas", repository.findByDonorId(donorId))
        return "labdatas"
    }

    @DeleteMapping(path = ["{labdataId}"])
    fun deleteLabData(@PathVariable donorId: Long, @PathVariable labdataId: Long, model: Model): String {
        repository.deleteById(labdataId)
        model.addAttribute("labdatas", repository.findByDonorId(donorId))
        return "labdatas"
    }

}
