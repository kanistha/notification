package org.notification.service

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.GetQueueUrlResult
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.services.sqs.model.SendMessageResult
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.notification.common.TestConstants.Companion.EMAIL
import org.notification.common.TestConstants.Companion.MESSAGE_ID
import org.notification.common.TestConstants.Companion.NAME
import org.notification.common.TestConstants.Companion.QUEUE_URL
import org.notification.common.TestConstants.Companion.USER_ID
import org.notification.event.UserEvent
import org.notification.model.User

class UserEventTest {

    @Mock
    private lateinit var sqs: AmazonSQS

    @InjectMocks
    private lateinit var subject: UserEvent

    @Before
    fun setUp() {

        initMocks(this)

        subject = UserEvent(sqs)

    }

    @Test
    fun `send - success`() {
        // given
        val user = User(
                id = USER_ID,
                name = NAME,
                email = EMAIL
        )

        given(sqs.getQueueUrl(anyString()))
                .willReturn(GetQueueUrlResult().withQueueUrl(QUEUE_URL))

        given(sqs.sendMessage(any()))
                .willReturn(SendMessageResult().withMessageId(MESSAGE_ID))

        // when
        val result = subject.send(user)

        // then
        assertThat(result).isEqualTo(MESSAGE_ID)

        verify(sqs).sendMessage(SendMessageRequest(
                QUEUE_URL,
                user.toJson()
        ))
    }

    private fun User.toJson(): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(this)
    }

}
