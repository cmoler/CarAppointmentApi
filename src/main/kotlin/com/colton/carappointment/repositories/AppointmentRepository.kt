package com.colton.carappointment.repositories

import com.colton.carappointment.entities.Appointment
import com.colton.carappointment.entities.AppointmentStatus
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import org.springframework.transaction.annotation.Transactional;

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Int> {

    fun findByStartDateAfterAndEndDateBefore(start: LocalDateTime, end: LocalDateTime, sort: Sort): List<Appointment>

    @Transactional
    @Modifying
    @Query("update Appointment a set a.status = :status where a.id = :id")
    fun updateStatus(@Param(value = "id")id: Int,  @Param(value = "status")status: AppointmentStatus)
}