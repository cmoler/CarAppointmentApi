package com.colton.carappointment.controllers

import com.colton.carappointment.entities.Appointment
import com.colton.carappointment.entities.AppointmentStatus
import com.colton.carappointment.services.AppointmentService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ["api/appointment"])
class AppointmentController(val appointmentService: AppointmentService) {

    @DeleteMapping("/delete/{id}")
    fun deleteAppointment(@PathVariable id: Int): ResponseEntity<Any> {
        return try{
            appointmentService.deleteAppointmentById(id)
            ResponseEntity.ok(null)
        } catch(ex: Exception) {
            ResponseEntity.status(400).build()
        }
    }

    @DeleteMapping("/deleteAll")
    fun deleteAllAppointments(): ResponseEntity<Void> {
        appointmentService.deleteAllAppointments()
        return ResponseEntity.ok(null)
    }

    @PostMapping
    fun createAppointment(@RequestBody appointment: Appointment): ResponseEntity<Appointment> {
        return ResponseEntity.ok(appointmentService.createNewAppointment(appointment))
    }

    @PutMapping("/update/{id}/{status}")
    fun updateAppointment(@PathVariable id: Int, @PathVariable status: AppointmentStatus): ResponseEntity<Void> {
        appointmentService.updateAppointmentStatus(id, status)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{id}")
    fun findAppointment(@PathVariable id: Int): ResponseEntity<Appointment?> {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id))
    }

    @GetMapping("/withinRange/{startDate}/{endDate}")
    fun findWithinRange(@PathVariable startDate: String, @PathVariable endDate: String): ResponseEntity<List<Appointment>> {
        return try{
            val parsedStart = LocalDateTime.parse(startDate)
            val parsedEnd = LocalDateTime.parse(endDate)

            ResponseEntity.ok(
                appointmentService.getAppointmentsWithInRangeSortedByPrice(
                    parsedStart,
                    parsedEnd
                )
            )
        }
        catch (exception: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}