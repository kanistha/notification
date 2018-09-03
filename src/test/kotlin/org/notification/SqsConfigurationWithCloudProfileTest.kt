package org.notification

import com.amazonaws.services.sqs.AmazonSQS
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class SqsConfigurationWithCloudProfileTest {
    
    @Autowired
    private lateinit var sqs: AmazonSQS

    @Test
    fun `loads with bean containing 'notification-queue'`() {
        assertThat(sqs).isInstanceOf(AmazonSQS::class.java)
    }
}