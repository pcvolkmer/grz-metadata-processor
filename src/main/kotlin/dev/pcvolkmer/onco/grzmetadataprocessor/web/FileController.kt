package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.data.File
import dev.pcvolkmer.onco.grzmetadataprocessor.data.FileRepository
import dev.pcvolkmer.onco.grzmetadataprocessor.data.LabDataRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping
class FileController(
    private val fileRepository: FileRepository,
    private val labDataRepository: LabDataRepository
) {

    @GetMapping(path = ["/labdatas/{labDataId}/files"])
    fun getAllFiles(@PathVariable labDataId: Long, model: Model): String {
        model.addAttribute("labDataId", labDataId)
        model.addAttribute("files", fileRepository.findByLabDataId(labDataId))
        return "files"
    }

    @GetMapping(path = ["/files/unused"])
    fun getAllUnusedFiles(model: Model): String {
        model.addAttribute("labDatas", labDataRepository.findAll().filterNot { it.einsendenummer.isBlank() })
        model.addAttribute("files", fileRepository.findByLabDataIdIsNull())
        return "unusedfiles"
    }

    @PostMapping(path = ["/labdatas/{labDataId}/files"])
    fun postFile(@PathVariable labDataId: Long, model: Model): String {
        fileRepository.save(File(labDataId = labDataId))
        model.addAttribute("labDataId", labDataId)
        model.addAttribute("files", fileRepository.findByLabDataId(labDataId))
        return "files"
    }

    @PutMapping(path = ["/labdatas/{labDataId}/files/{fileId}"])
    fun putFile(@PathVariable labDataId: Long, @PathVariable fileId: Long, file: File, model: Model): String {
        fileRepository.save(file)
        model.addAttribute("labDataId", labDataId)
        model.addAttribute("files", fileRepository.findByLabDataId(labDataId))
        return "files"
    }

    @PutMapping(path = ["/files/unused/{fileId}"])
    fun putUnusedFile(@PathVariable fileId: Long, file: File, model: Model): String {
        fileRepository.save(file)
        model.addAttribute("labDatas", labDataRepository.findAll().filterNot { it.einsendenummer.isBlank() })
        model.addAttribute("files", fileRepository.findByLabDataIdIsNull())
        return "unusedfiles"
    }

    @DeleteMapping(path = ["/labdatas/{labDataId}/files/{fileId}"])
    fun deleteFile(@PathVariable fileId: Long, @PathVariable labDataId: Long, model: Model): String {
        val file = fileRepository.findById(fileId)
        file.ifPresent {
            it.apply { this.labDataId = null }
            fileRepository.save(it)
        }
        model.addAttribute("fileId", fileId)
        model.addAttribute("files", fileRepository.findByLabDataId(labDataId))
        return "files"
    }

    @DeleteMapping(path = ["/files/unused/{fileId}"])
    fun deleteUnusedFile(@PathVariable fileId: Long, model: Model): String {
        fileRepository.deleteById(fileId)
        model.addAttribute("files", fileRepository.findByLabDataIdIsNull())
        return "unusedfiles"
    }

}
