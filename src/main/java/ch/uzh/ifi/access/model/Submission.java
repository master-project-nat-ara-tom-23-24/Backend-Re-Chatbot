package ch.uzh.ifi.access.model;

import ch.uzh.ifi.access.model.constants.SubmissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Submission {
    @Id
    @GeneratedValue
    private Long id;

    private Integer ordinalNum;

    @Column(nullable = false)
    private String userId;

    private Double points;

    private boolean valid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubmissionType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime nextAttemptAt;

    @JsonIgnore
    @Column(columnDefinition = "text")
    private String logs;

    @Column(columnDefinition = "text")
    private String output;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    private List<SubmissionFile> files = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Double getMaxPoints() {
        return task.getMaxPoints();
    }

    public boolean isGraded() {
        return type.isGraded();
    }

    public String getName() {
        return isGraded() ? "Submission " + ordinalNum.toString() : task.getEvaluator().formCommand(type);
    }
}
