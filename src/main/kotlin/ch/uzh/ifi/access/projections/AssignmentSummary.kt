package ch.uzh.ifi.access.projections

import ch.uzh.ifi.access.model.Assignment
import ch.uzh.ifi.access.model.AssignmentInformation
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.rest.core.config.Projection
import java.time.LocalDateTime

@Projection(types = [Assignment::class])
interface AssignmentSummary {
    val slug: String?
    val information: Map<String?, AssignmentInformation?>?
    val start: LocalDateTime?
    val end: LocalDateTime?
    @get:Value("#{@courseService.calculateAssignmentMaxPoints(target.tasks, null)}")
    val maxPoints: Double?
    val tasks: List<TaskSummary?>?
}