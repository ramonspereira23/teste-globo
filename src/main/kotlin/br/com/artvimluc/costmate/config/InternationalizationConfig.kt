package br.com.artvimluc.costmate.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@Configuration
class InternationalizationConfig : WebMvcConfigurer {

    @Bean
    fun localeResolver(): SessionLocaleResolver {
        val localeResolver = SessionLocaleResolver()
        localeResolver.setDefaultLocale(Locale.getDefault())
        return localeResolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeInterceptor = LocaleChangeInterceptor()
        localeInterceptor.paramName = "lang"
        return localeInterceptor
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }
}
