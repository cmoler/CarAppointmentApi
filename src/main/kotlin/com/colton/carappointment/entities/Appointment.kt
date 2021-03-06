package com.colton.carappointment.entities

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Data
@Entity
data class Appointment (

    @Id @GeneratedValue
    var id: Int? = null,

    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val price: Double,
    var status: AppointmentStatus
)

enum class AppointmentStatus {
    ACTIVE, CANCELLED
}