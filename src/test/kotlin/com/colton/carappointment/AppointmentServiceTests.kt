package com.colton.carappointment

import com.colton.carappointment.entities.Appointment
import com.colton.carappointment.entities.AppointmentStatus
import com.colton.carappointment.services.AppointmentService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDateTime

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class AppointmentServiceTests {

    @Autowired
    private lateinit var appointmentService: AppointmentService

    val appointment1Active = Appointment(
        1,
        LocalDateTime.parse("2018-11-28T18:35:24"),
        LocalDateTime.parse("2018-11-28T18:35:24"),
        120,
        AppointmentStatus.ACTIVE
    )
    val appointment2Active = Appointment(
        2,
        LocalDateTime.parse("2018-12-01T18:35:24"),
        LocalDateTime.parse("2018-12-01T18:35:24"),
        14,
        AppointmentStatus.ACTIVE
    )
    val appointment3Active = Appointment(
        3,
        LocalDateTime.parse("2018-12-03T18:35:24"),
        LocalDateTime.parse("2018-12-04T18:35:24"),
        1,
        AppointmentStatus.ACTIVE
    )
    val appointment4Cancelled = Appointment(
        4,
        LocalDateTime.parse("2018-11-30T18:35:24"),
        LocalDateTime.parse("2018-12-02T18:35:24"),
        100,
        AppointmentStatus.CANCELLED
    )

    @Test
    fun shouldSaveAppointment() {
        appointmentService.createNewAppointment(appointment1Active)

        val result = appointmentService.getAppointmentById(appointment1Active.id!!)

        Assertions.assertNotNull(result)
        Assertions.assertNotNull(result?.id)
    }

    @Test
    fun shouldDeleteAllAppointments() {
        appointmentService.createNewAppointment(appointment1Active)
        appointmentService.createNewAppointment(appointment2Active)
        appointmentService.createNewAppointment(appointment3Active)
        appointmentService.createNewAppointment(appointment4Cancelled)

        appointmentService.deleteAllAppointments()

        Assertions.assertNull(appointmentService.getAppointmentById(appointment1Active.id!!))
        Assertions.assertNull(appointmentService.getAppointmentById(appointment2Active.id!!))
        Assertions.assertNull(appointmentService.getAppointmentById(appointment3Active.id!!))
        Assertions.assertNull(appointmentService.getAppointmentById(appointment4Cancelled.id!!))
    }

    @Test
    fun shouldDeleteAppointmentById() {
        appointmentService.createNewAppointment(appointment1Active)
        appointmentService.createNewAppointment(appointment2Active)
        appointmentService.createNewAppointment(appointment3Active)
        appointmentService.createNewAppointment(appointment4Cancelled)

        appointmentService.deleteAppointmentById(appointment3Active.id!!)

        Assertions.assertNotNull(appointmentService.getAppointmentById(appointment1Active.id!!))
        Assertions.assertNotNull(appointmentService.getAppointmentById(appointment2Active.id!!))
        Assertions.assertNull(appointmentService.getAppointmentById(appointment3Active.id!!))
        Assertions.assertNotNull(appointmentService.getAppointmentById(appointment4Cancelled.id!!))
    }

    @Test
    fun shouldUpdateAppointmentStatus() {
        appointmentService.createNewAppointment(appointment4Cancelled)

        val appointment = appointmentService.getAppointmentById(appointment1Active.id!!)

        Assertions.assertNotNull(appointment)
        Assertions.assertEquals(AppointmentStatus.CANCELLED, appointment?.status)

        appointmentService.updateAppointmentStatus(appointment?.id!!, AppointmentStatus.ACTIVE)

        val updatedAppointment = appointmentService.getAppointmentById(appointment1Active.id!!)

        Assertions.assertNotNull(updatedAppointment)
        Assertions.assertEquals(AppointmentStatus.ACTIVE, updatedAppointment?.status)
    }

    @Test
    fun shouldGetAppointmentById() {
        appointmentService.createNewAppointment(appointment1Active)

        val result = appointmentService.getAppointmentById(appointment1Active.id!!)

        Assertions.assertNotNull(result)
        Assertions.assertNotNull(result?.id)
    }

    @Test
    fun shouldGetAppointmentWithinRangeAndSorted() {
        appointmentService.createNewAppointment(appointment1Active)
        appointmentService.createNewAppointment(appointment2Active)
        appointmentService.createNewAppointment(appointment3Active)
        appointmentService.createNewAppointment(appointment4Cancelled)

        val start = LocalDateTime.parse("2018-11-27T18:35:24")
        val end = LocalDateTime.parse("2018-12-03T18:35:24")

        val result = appointmentService.getAppointmentsWithInRangeSortedByPrice(start, end)

        Assertions.assertEquals(3, result?.size)
        Assertions.assertEquals(14, result!![0].price)
        Assertions.assertEquals(100, result[1].price)
        Assertions.assertEquals(120, result[2].price)
    }
}