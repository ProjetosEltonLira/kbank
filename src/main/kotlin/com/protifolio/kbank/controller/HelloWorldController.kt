package com.protifolio.kbank.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloWorldController {

    @GetMapping
    fun helloWorld(): ResponseEntity<String> {
        println("Dentro da controller")
        return ResponseEntity.ok().build()
    }
}