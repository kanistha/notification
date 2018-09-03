package org.notification.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class JsonConverter {

    companion object {
        fun toJson(obj: Any): String {
            val objectMapper = ObjectMapper()
            return objectMapper.writeValueAsString(obj)
        }

    }
}