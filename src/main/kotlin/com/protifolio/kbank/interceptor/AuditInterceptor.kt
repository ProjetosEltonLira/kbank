package com.protifolio.kbank.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView


@Component
class AuditInterceptor : HandlerInterceptor {
    private val logger: Logger = LoggerFactory.getLogger(AuditInterceptor::class.java)


    // Executa antes de chegar na controller
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val ipAddress = request.remoteAddr
        val requestUri = request.requestURI
        //logger.info("Request recebida: IP=$ipAddress, URI=$requestUri, Method=${request.method}")
        return true // Retorna true para continuar a execução dos demais Interceptors
    }

    // Executa depois da execução do handler, antes de renderizar a view
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
       // logger.info("Manipular execução: URI=${request.requestURI}, Status=${response.status}")
    }

    // Executa após a conclusão completa da requisição (inclui rendering da view)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        logger.info("Auditoria - Metodo: : " +
                "IP=${request.remoteAddr}, " +
                "URI=${request.requestURI}, " +
                "Method=${request.method}, " +
                "StatusCode=${response.status}")
        /* Não usado
        if (ex != null) {
            logger.error("Requisição completa com exceção: URI=${request.requestURI}, Exceção=${ex.message}")
        } else {
            logger.info("Requisição completa com sucesso: URI=${request.requestURI}, Status=${response.status}")
        }*/
    }
}
