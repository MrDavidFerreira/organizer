package com.davfer.organizer.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
