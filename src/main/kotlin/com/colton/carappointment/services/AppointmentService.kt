package com.colton.carappointment.services

import com.colton.carappointment.entities.Appointment
import com.colton.carappointment.entities.AppointmentStatus
import com.colton.carappointment.repositories.AppointmentRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@RequiredArgsConstructor
class AppointmentService(val appointmentRepository: AppointmentRepository) {


    fun deleteAllAppointments() {
        appointmentRepository.deleteAll();
    }

    fun deleteAppointmentById(id: Int) {
        appointmentRepository.deleteById(id)
    }

    fun createNewAppointment(appointment: Appointment) {
        appointmentRepository.save(appointment);
    }

    fun updateAppointmentStatus(id: Int, status: AppointmentStatus) {
        appointmentRepository.updateStatus(id, status)
    }

    fun getAppointmentById(id: Int): Appointment? {
        return try{
            appointmentRepository.getById(id)
        } catch (exception: Exception) {
            null
        }
    }

    fun getAppointmentsWithInRangeSortedByPrice(start: LocalDateTime, end: LocalDateTime): List<Appointment>? {
        return try {
            appointmentRepository
            .findByStartDateAfterAndEndDateBefore(
                start,
                end,
                Sort.by("price")
            )
        } catch (exception: Exception) {
            null
        }
    }
}