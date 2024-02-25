package ch.uzh.ifi.access.service
import ch.uzh.ifi.access.model.dto.CourseDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.core.env.Environment
import java.nio.file.Path


@Configuration
class ChatbotServiceConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}

data class ChatbotData(
    val course: String,
    val slug: String
)

@Service
class ChatbotService(private val restTemplate: RestTemplate, private val env: Environment) {
    
    fun createContext(slug: String, coursePath: Path) {
        //this should be changed and put in a config file
        val chatbotApiUrl = env.getProperty("CONTEXT_SERVICE_URL", "http://127.0.0.1:3423/contexts/create") 
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val chatbotData = ChatbotData(course = coursePath.fileName.toString(), slug = slug)
        val requestEntity = HttpEntity(chatbotData, headers)
        restTemplate.exchange(chatbotApiUrl, HttpMethod.PUT, requestEntity, String::class.java)
    }

}