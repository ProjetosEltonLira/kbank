package com.portifolio.kbank.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class IntercepterConfig(
    private val auditInterceptor: AuditInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
      registry.addInterceptor(auditInterceptor)
    }
}