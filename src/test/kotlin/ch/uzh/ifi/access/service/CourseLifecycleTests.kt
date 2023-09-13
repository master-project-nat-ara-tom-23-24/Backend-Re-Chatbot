package ch.uzh.ifi.access.service

import ch.uzh.ifi.access.BaseTest
import ch.uzh.ifi.access.projections.CourseWorkspace
import com.fasterxml.jackson.databind.json.JsonMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.nio.file.Paths
import java.time.LocalDateTime


@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CourseLifecycleTests(
    @Autowired val mvc: MockMvc,
    @Autowired val jsonMapper: JsonMapper,
    @Autowired val courseLifecycle: CourseLifecycle,
    @Autowired val courseService: CourseService) : BaseTest() {

    /* TODO: find a way to reset the db before/after testing
             This is a bit tricky, because at the moment, both ACCESS and keycloak use the same postgres database,
             so something like https://github.com/zonkyio/embedded-database-spring-test doesn't work, because that
             temporary database lacks all the keycloak data, like roles, etc.
     */

    @Test
    @WithMockUser(username="supervisor@uzh.ch", authorities = ["supervisor"])
    @Order(0)
    @Disabled
    fun `Course import succeeds`() {
        // delete existing course if any
        try {
            courseService.deleteCourse("access-mock-course")
        } catch (_: ResponseStatusException) {
        }

        val path = "Mock-Course-Re"
        val file = File(path)
        val absolutePath = Paths.get(file.absolutePath)

        courseLifecycle.createFromDirectory(
            absolutePath,
            "https://github.com/mp-access/Mock-Course-Re.git"
        )

        courseService.getCourseBySlug("access-mock-course")
    }

    fun getCourse(): CourseWorkspace {
        return courseService.getCourseWorkspaceBySlug("access-mock-course")
    }

    @Test
    @WithMockUser(username="supervisor@uzh.ch", authorities = ["supervisor"])
    fun `Imported course slug correct`() {
        assertEquals("access-mock-course", getCourse().slug)
    }
    @Test
    @WithMockUser(username="supervisor@uzh.ch", authorities = ["supervisor"])
    fun `Imported course override end date correct`() {
        assertEquals(LocalDateTime.of(2028,1,1,13,0), getCourse().overrideEnd)
    }
    @Test
    @WithMockUser(username="supervisor@uzh.ch", authorities = ["supervisor"])
    fun `Imported course number of assignments correct`() {
        getCourse().assignments?.let { assertEquals(3, it.size) }
    }
    @Test
    @WithMockUser(username="supervisor@uzh.ch", authorities = ["supervisor"])
    fun `Imported course assignment numbers correct`() {
        assertEquals(setOf(1,2,3), getCourse().assignments?.map{ it?.ordinalNum }?.distinct()?.toSet())
    }

}

