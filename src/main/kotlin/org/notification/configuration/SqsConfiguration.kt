package org.notification.configuration

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.regions.Regions.US_EAST_1
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableSqs
class SqsConfiguration {

    @Bean
    @ConditionalOnProperty(value = ["application.localStack"], havingValue = "true")
    fun amazonSQS(): AmazonSQSBufferedAsyncClient {

        return AmazonSQSBufferedAsyncClient(AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(EndpointConfiguration("http://localhost:4576", US_EAST_1.name))
                .build())
    }
}