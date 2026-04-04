package cz.cyberrange.platform.training.adaptive.persistence.entity.training;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Tracks how many times one participant accessed one training definition.
 */
@Entity
@Table(name = "training_definition_access_guard",
        uniqueConstraints = @UniqueConstraint(columnNames = {"participant_ref_id", "training_definition_id"}))
public class TrainingDefinitionAccessGuard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainingDefinitionAccessGuardGenerator")
    @SequenceGenerator(name = "trainingDefinitionAccessGuardGenerator", sequenceName = "training_definition_access_guard_seq")
    @Column(name = "training_definition_access_guard_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "participant_ref_id", nullable = false)
    private Long participantRefId;

    @Column(name = "training_definition_id", nullable = false)
    private Long trainingDefinitionId;

    @Column(name = "access_count", nullable = false)
    private Integer accessCount;

    @Column(name = "banned", nullable = false)
    private boolean banned;

    @Column(name = "banned_at")
    private LocalDateTime bannedAt;

    @Column(name = "last_access_at", nullable = false)
    private LocalDateTime lastAccessAt;

    public TrainingDefinitionAccessGuard() {
    }

    public TrainingDefinitionAccessGuard(Long participantRefId, Long trainingDefinitionId, Integer accessCount, boolean banned,
                                        LocalDateTime bannedAt, LocalDateTime lastAccessAt) {
        this.participantRefId = participantRefId;
        this.trainingDefinitionId = trainingDefinitionId;
        this.accessCount = accessCount;
        this.banned = banned;
        this.bannedAt = bannedAt;
        this.lastAccessAt = lastAccessAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParticipantRefId() {
        return participantRefId;
    }

    public void setParticipantRefId(Long participantRefId) {
        this.participantRefId = participantRefId;
    }

    public Long getTrainingDefinitionId() {
        return trainingDefinitionId;
    }

    public void setTrainingDefinitionId(Long trainingDefinitionId) {
        this.trainingDefinitionId = trainingDefinitionId;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public LocalDateTime getBannedAt() {
        return bannedAt;
    }

    public void setBannedAt(LocalDateTime bannedAt) {
        this.bannedAt = bannedAt;
    }

    public LocalDateTime getLastAccessAt() {
        return lastAccessAt;
    }

    public void setLastAccessAt(LocalDateTime lastAccessAt) {
        this.lastAccessAt = lastAccessAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingDefinitionAccessGuard)) return false;
        TrainingDefinitionAccessGuard that = (TrainingDefinitionAccessGuard) o;
        return Objects.equals(getParticipantRefId(), that.getParticipantRefId())
                && Objects.equals(getTrainingDefinitionId(), that.getTrainingDefinitionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParticipantRefId(), getTrainingDefinitionId());
    }

    @Override
    public String toString() {
        return "TrainingDefinitionAccessGuard{" +
                "id=" + id +
                ", participantRefId=" + participantRefId +
                ", trainingDefinitionId=" + trainingDefinitionId +
                ", accessCount=" + accessCount +
                ", banned=" + banned +
                ", bannedAt=" + bannedAt +
                ", lastAccessAt=" + lastAccessAt +
                '}';
    }
}
