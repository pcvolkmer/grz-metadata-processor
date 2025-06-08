package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.Donor
import dev.pcvolkmer.onco.grzmetadataprocessor.data.DonorRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/cases/{caseId}/donors")
class DonorController(
    private val repository: DonorRepository
) {

    @GetMapping
    fun getAllDonors(@PathVariable caseId: Long, model: Model): String {
        model.addAttribute("caseId", caseId)
        model.addAttribute("donors", repository.findByCaseId(caseId))
        return "donors"
    }

    @PostMapping
    fun postDonor(@PathVariable caseId: Long, model: Model): String {
        repository.save(Donor(caseId = caseId))
        model.addAttribute("caseId", caseId)
        model.addAttribute("donors", repository.findByCaseId(caseId))
        return "donors"
    }

    @PutMapping(path = ["{donorId}"])
    fun postDonor(@PathVariable caseId: Long, @PathVariable donorId: Long, donor: Donor, model: Model): String {
        repository.save(donor)
        model.addAttribute("caseId", caseId)
        model.addAttribute("donors", repository.findByCaseId(caseId))
        return "donors"
    }

    @DeleteMapping(path = ["{donorId}"])
    fun deleteDonor(@PathVariable caseId: Long, @PathVariable donorId: Long, model: Model): String {
        repository.deleteById(donorId)
        model.addAttribute("donors", repository.findByCaseId(caseId))
        return "donors"
    }

}
