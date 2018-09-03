package org.notification

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.notification.common.TestConstants.Companion.EMAIL
import org.notification.common.TestConstants.Companion.NAME
import org.notification.event.UserEvent
import org.notification.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser

class UserIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userEvent: UserEvent

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Test
    fun `createUser - success`() {
        // create user
        val result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("""{"name" : "$NAME","email" : "$EMAIL"}"""))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").isNotEmpty)
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andReturn()

        // get user
        val savedUser = objectMapper.readValue(result.response.contentAsString, User::class.java)

        mockMvc.perform(get("/users/{id}", savedUser.id))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").isNotEmpty)
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))

        // get user event
        val user = userEvent.get()
        assertThat(user).isEqualTo(savedUser.toJson())
    }

    private fun User.toJson() = objectMapper.writeValueAsString(this)

}