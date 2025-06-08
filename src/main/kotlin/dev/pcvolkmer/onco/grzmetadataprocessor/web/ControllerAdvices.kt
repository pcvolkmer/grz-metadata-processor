package dev.pcvolkmer.onco.grzmetadataprocessor.web

import dev.pcvolkmer.onco.grzmetadataprocessor.exceptions.NotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvices {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): String {
        return "error/notfound"
    }

}
