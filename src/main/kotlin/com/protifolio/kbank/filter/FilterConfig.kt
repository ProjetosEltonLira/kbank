package com.protifolio.kbank.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FilterConfig(
    private val ipFilter: IpFilter) {

    @Bean
    fun ipFilterRegistrationBean(): FilterRegistrationBean<IpFilter> {
        var registrationBean = FilterRegistrationBean<IpFilter>()

        // aqui é possível setar a ordem de execução dos filters
        registrationBean.filter = ipFilter
        registrationBean.order = 0

        return registrationBean
    }
}
