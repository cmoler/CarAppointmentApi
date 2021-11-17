package com.colton.carappointment.services

import com.colton.carappointment.entities.Appointment
import com.colton.carappointment.entities.AppointmentStatus
import lombok.RequiredArgsConstructor
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.logging.Level
import java.util.logging.Logger


@Component
@RequiredArgsConstructor
class RandomAppointmentCreation(val appointmentService: AppointmentService) {

    val delay: Number = 30000
    @Scheduled(fixedDelayString = "#{new Double((T(java.lang.Math).random() + 1) * 5000).intValue()}")
    fun createAppointment() {
        // Create an appointment for the next 30 minutes
        val appointment = Appointment(
            null,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(30),
            Math.random() * 20,
            AppointmentStatus.ACTIVE
        )

        appointmentService.createNewAppointment(appointment)
        log.log(Level.INFO, "created appointment", appointment)
    }

    companion object {
        val log = Logger.getLogger(RandomAppointmentCreation::class.toString())
    }
}