package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.Case
import dev.pcvolkmer.onco.grzmetadataprocessor.data.CaseRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.exceptions.NotFoundException
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping(path = ["/cases"])
class CaseController(
    private val repository: CaseRepository
) {

    @GetMapping
    fun getAllCase(model: Model): String {
        model.addAttribute("cases", repository.findAll())
        return "cases"
    }

    @GetMapping(path = ["{caseId}"])
    fun getCase(@PathVariable caseId: Long, model: Model): String {
        repository.findById(caseId).ifPresentOrElse({
            model.addAttribute("case", it)
        }, {
            throw NotFoundException()
        })
        return "case"
    }

    @PostMapping
    fun postCase(model: Model): ResponseEntity<Unit> {
        val createdCase = repository.save(Case())
        model.addAttribute("cases", repository.findAll())
        return ResponseEntity
            .noContent()
            .headers { headers ->
                headers.add("HX-Redirect", "/cases/${createdCase.id}")
            }
            .build()
    }

    @PutMapping(path = ["{caseId}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun putCase(@PathVariable caseId: Long, case: Case, model: Model): String {
        val savedCase = repository.save(case)
        model.addAttribute("case", savedCase)
        return "case"
    }

    @DeleteMapping(path = ["{caseId}"])
    fun deleteCase(@PathVariable caseId: Long, model: Model): ResponseEntity<Unit> {
        repository.deleteById(caseId)
        model.addAttribute("cases", repository.findAll())
        return ResponseEntity
            .noContent()
            .headers { headers ->
                headers.add("HX-Redirect", "/")
            }
            .build()
    }

}
