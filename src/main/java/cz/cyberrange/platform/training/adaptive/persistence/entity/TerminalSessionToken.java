package cz.cyberrange.platform.training.adaptive.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity representing a short-lived session token used for terminal-based exam access (Deep Link URL).
 * Tokens are one-time-use and expire after 300 seconds.
 */
@Entity
@Table(name = "terminal_session_token")
@NamedQueries({
        @NamedQuery(
                name = "TerminalSessionToken.findValidByToken",
                query = "SELECT t FROM TerminalSessionToken t WHERE t.token = :token AND t.used = false AND t.expiresAt > :now"
        )
})
public class TerminalSessionToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "terminalSessionTokenGenerator")
    @SequenceGenerator(name = "terminalSessionTokenGenerator", sequenceName = "terminal_session_token_seq")
    @Column(name = "terminal_session_token_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "token", nullable = false, unique = true, length = 36)
    private String token;

    @Column(name = "training_run_id", nullable = false)
    private Long trainingRunId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "jwt_token", nullable = false, columnDefinition = "TEXT")
    private String jwtToken;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "session_state", length = 64)
    private String sessionState;

    @Column(name = "access_token", length = 64)
    private String accessToken;

    public TerminalSessionToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTrainingRunId() {
        return trainingRunId;
    }

    public void setTrainingRunId(Long trainingRunId) {
        this.trainingRunId = trainingRunId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TerminalSessionToken)) return false;
        TerminalSessionToken that = (TerminalSessionToken) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getToken());
    }

    @Override
    public String toString() {
        return "TerminalSessionToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", trainingRunId=" + trainingRunId +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", used=" + used +
                ", jwtToken='[REDACTED]'" +
                '}';
    }
}
