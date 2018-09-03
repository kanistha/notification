package org.notification.event

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import org.notification.model.User
import org.notification.utils.JsonConverter
import org.springframework.stereotype.Service

@Service
class UserEvent(private val sqs: AmazonSQS) {

    private val queueName = "notification_queue"

    fun send(user: User): String {


        val queueUrl = sqs.getQueueUrl(queueName).queueUrl
        val message = JsonConverter.toJson(user)
        val request = SendMessageRequest(queueUrl, message)

        return sqs.sendMessage(request).messageId

    }

    fun get(): String {

        val queueUrl = sqs.getQueueUrl(queueName).queueUrl

        val messages = sqs.receiveMessage(queueUrl)

        return messages.messages[0].body

    }

}
