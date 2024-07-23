package br.com.artvimluc.costmate.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service


@Service
class MessageLocator

@Autowired
constructor (
    private val messageSource: MessageSource,
) {
    fun getMessage(code: String): String =
        messageSource.getMessage(code, null, LocaleContextHolder.getLocale())
}