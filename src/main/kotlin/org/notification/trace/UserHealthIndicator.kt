package org.notification.trace

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class UserHealthIndicator: HealthIndicator {

    override
    fun health(): Health {
        return  Health.status("Production").build()
    }

}