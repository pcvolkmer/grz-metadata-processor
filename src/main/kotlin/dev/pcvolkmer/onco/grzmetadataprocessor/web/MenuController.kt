package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.CaseRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.data.FileRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataProfileRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MenuController(
    private val caseRepository: CaseRepository,
    private val labDataRepository: LabDataRepository,
    private val fileRepository: FileRepository,
    private val labDataProfileRepository: LabDataProfileRepository
) {

    @GetMapping("/cases/menu")
    fun getCaseMenus(model: Model): String {
        model.addAttribute("cases", caseRepository.findAll().count())
        model.addAttribute("unusedLabData", labDataRepository.countLabDataByDonorIdIsNull())
        model.addAttribute("unusedFiles", fileRepository.findByLabDataIdIsNull().size)
        model.addAttribute("labDataProfiles", labDataProfileRepository.count())
        return "casemenu"
    }

}
