package ch.uzh.ifi.access.service
import ch.uzh.ifi.access.model.dto.CourseDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

@Configuration
open class ChatbotServiceConfig {

    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}

data class ChatbotContextRequest(val course: String, val slug: String)

@Service
class ChatbotService(private val restTemplate: RestTemplate) {
    fun createContext(fileName:String, courseSlug:String?) {
        val chatbotApiUrl = "http://127.0.0.1:3423/contexts/create"
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity = HttpEntity(ChatbotContextRequest(fileName,courseSlug.toString()), headers)
        restTemplate.exchange(chatbotApiUrl, HttpMethod.PUT, requestEntity, String::class.java)
    }

}