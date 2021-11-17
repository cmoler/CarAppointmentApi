package com.colton.carappointment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class CarAppointmentApplication

fun main(args: Array<String>) {
    runApplication<CarAppointmentApplication>(*args)
}
