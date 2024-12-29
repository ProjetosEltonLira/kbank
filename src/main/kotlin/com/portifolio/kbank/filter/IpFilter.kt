package com.portifolio.kbank.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component


//Essa classe não interage com o contexto do Spring, pode ser utilizado em qualquer outro frameWork
@Component
class IpFilter : HttpFilter() {

    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        val ipAddress = request.remoteAddr // Obtém o IP do cliente
        println("IP Address: $ipAddress") // Ou faça um log para monitorar os IPs

        response.setHeader("x-user-ip", ipAddress)
        request.setAttribute("x-user-ip", ipAddress) //passa para todas as proximas requisições IP

        //response.status = 503 //isso pararia a execução da api

        // Caso queira aplicar alguma regra de bloqueio:
        /*if (ipAddress == "192.168.0.1") {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied")
            return
        }*/

        // Continua o processamento da requisição
        chain.doFilter(request, response)


    }
}