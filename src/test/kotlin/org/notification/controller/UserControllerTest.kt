package org.notification.controller

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner
import org.notification.common.TestConstants.Companion.EMAIL
import org.notification.common.TestConstants.Companion.NAME
import org.notification.common.TestConstants.Companion.USER_ID
import org.notification.model.User
import org.notification.event.UserEvent
import org.notification.service.UserService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(MockitoJUnitRunner::class)
class UserControllerTest {

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var userEvent: UserEvent

    private lateinit var subject: UserController

    @Before
    fun setUp() {
        initMocks(this)

        subject = UserController(
                userService = userService,
                userEvent = userEvent
        )

        mockMvc = MockMvcBuilders.standaloneSetup(subject)
                .build()
    }

    @Test
    fun `getUser - success`() {
        // given
        val user = User(
                id = USER_ID,
                name = NAME,
                email = EMAIL
        )
        given(userService.getUser(any()))
                .willReturn(user)

        // when
        val result = mockMvc.perform(get("/users/{id}", USER_ID))

        // then
        result
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").isNotEmpty)
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))

        verify(userService).getUser(USER_ID)
    }

    @Test
    fun `createUser - success`() {
        // given
        val user = User(
                id = USER_ID,
                name = NAME,
                email = EMAIL
        )
        given(userService.saveUser(any()))
                .willReturn(user)

        // when
        val result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("""{"name" : "$NAME","email" : "$EMAIL"}"""))

        // then
        result
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").isNotEmpty)
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
    }

    @Test
    fun `createUser - should send user event to sqs after creating user`() {
        // given
        val user = User(
                id = USER_ID,
                name = NAME,
                email = EMAIL
        )
        given(userService.saveUser(any()))
                .willReturn(user)

        // when
        val result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("""{"name" : "$NAME","email" : "$EMAIL"}"""))

        // then
        result
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").isNotEmpty)
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL))

        verify(userEvent).send(user)

    }


}