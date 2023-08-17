package ch.uzh.ifi.access.projections

import ch.uzh.ifi.access.model.*
import ch.uzh.ifi.access.model.dao.Timer
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.rest.core.config.Projection
import java.time.LocalDateTime

@Projection(types = [Assignment::class])
interface AssignmentWorkspace {
    val id: Long?
    val slug: String?
    val ordinalNum: Int?
    val information: Map<String?, AssignmentInformation?>?
    val start: LocalDateTime?
    val end: LocalDateTime?

    @get:Value("#{target.start}")
    @get:JsonFormat(pattern = "yyyy-MM-dd")
    val publishedDate: LocalDateTime?

    @get:Value("#{target.start}")
    @get:JsonFormat(pattern = "HH:mm")
    val publishedTime: LocalDateTime?

    @get:Value("#{target.end}")
    @get:JsonFormat(pattern = "yyyy-MM-dd")
    val dueDate: LocalDateTime?

    @get:Value("#{target.end}")
    @get:JsonFormat(pattern = "HH:mm")
    val dueTime: LocalDateTime?
    val countDown: List<Timer?>?
    val isPastDue: Boolean
    val isActive: Boolean
    @get:Value("#{@courseService.calculateAssignmentMaxPoints(target.tasks, null)}")
    val maxPoints: Double?

    @get:Value("#{@courseService.calculateAssignmentPoints(target.tasks, null)}")
    val points: Double?
    val tasks: List<TaskOverview?>?
}