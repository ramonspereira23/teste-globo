package br.com.artvimluc.costmate.web.advice

import br.com.artvimluc.costmate.exception.AllReadyExistsException
import br.com.artvimluc.costmate.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorBody> {
        logger.warn(ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorBody( statusCode = HttpStatus.NOT_FOUND.value(), message = ex.message))
    }

    @ExceptionHandler(AllReadyExistsException::class)
    fun handleExistsException(ex: AllReadyExistsException): ResponseEntity<ErrorBody> {
        logger.warn(ex.message)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorBody( statusCode = HttpStatus.CONFLICT.value(), message = ex.message))
    }

    // Adicione outros handlers de exceção conforme necessário
}