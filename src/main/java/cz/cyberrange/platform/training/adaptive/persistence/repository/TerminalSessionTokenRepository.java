package cz.cyberrange.platform.training.adaptive.persistence.repository;

import cz.cyberrange.platform.training.adaptive.persistence.entity.TerminalSessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The JPA repository interface to manage {@link TerminalSessionToken} instances.
 */
@Repository
public interface TerminalSessionTokenRepository extends JpaRepository<TerminalSessionToken, Long> {

    /**
     * Finds a token that has not been used and has not yet expired.
     *
     * @param token the token string (UUID)
     * @param now   the current timestamp used to check expiry
     * @return an {@link Optional} containing the valid {@link TerminalSessionToken}, or empty if not found
     */
    @Query("SELECT t FROM TerminalSessionToken t WHERE t.token = :token AND t.used = false AND t.expiresAt > :now")
    Optional<TerminalSessionToken> findValidByToken(@Param("token") String token, @Param("now") LocalDateTime now);
}
