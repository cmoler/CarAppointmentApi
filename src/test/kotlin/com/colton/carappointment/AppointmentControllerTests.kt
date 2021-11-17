package com.colton.carappointment


import com.colton.carappointment.entities.Appointment
import com.colton.carappointment.entities.AppointmentStatus
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import lombok.SneakyThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.charset.Charset
import java.time.LocalDateTime


@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var webApplicationContext: WebApplicationContext

	@Autowired
	lateinit var objectMapper: ObjectMapper

	val APPLICATION_JSON_UTF8: MediaType =
		MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))

	@BeforeEach
	fun before() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.build()
	}

	@SneakyThrows
	@Test
	fun shouldCallDeleteOne() {
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/api/appointment/delete/1")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().is4xxClientError)
	}

	@SneakyThrows
	@Test
	fun shouldCallDeleteAll() {
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/api/appointment/deleteAll")
		).andExpect(MockMvcResultMatchers.status().isOk)
	}

	@SneakyThrows
	@Test
	fun shouldCallCreateOne() {
		val appointment = Appointment(
			1,
			LocalDateTime.parse("2018-11-28T18:35:24"),
			LocalDateTime.parse("2018-11-28T18:35:24"),
			120.0,
			AppointmentStatus.ACTIVE
		)

		val mapper = ObjectMapper()
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
		val requestJson: String = objectMapper.writeValueAsString(appointment)

		mockMvc.perform(
			MockMvcRequestBuilders.post("/api/appointment/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson)
		).andExpect(MockMvcResultMatchers.status().isOk)
	}

	@SneakyThrows
	@Test
	fun shouldCallUpdateOne() {
		mockMvc.perform(
			MockMvcRequestBuilders.put("/api/appointment/update/1/ACTIVE")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk)
	}

	@SneakyThrows
	@Test
	fun shouldCallGetOne() {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/appointment/1")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk)
	}

	@SneakyThrows
	@Test
	fun shouldCallGetWithinRange() {
		mockMvc.perform(
			MockMvcRequestBuilders.get(
				"/api/appointment/withinRange/2018-11-28T18:35:24/2018-11-28T18:35:24")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk)
	}
}
