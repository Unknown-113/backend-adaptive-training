package cz.cyberrange.platform.training.adaptive.persistence;

import cz.cyberrange.platform.training.adaptive.persistence.entity.TerminalSessionToken;
import cz.cyberrange.platform.training.adaptive.persistence.repository.TerminalSessionTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TerminalSessionTokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TerminalSessionTokenRepository repository;

    private TerminalSessionToken buildToken(String uuid, Long trainingRunId, LocalDateTime expiresAt, boolean used) {
        TerminalSessionToken t = new TerminalSessionToken();
        t.setToken(uuid);
        t.setTrainingRunId(trainingRunId);
        t.setCreatedAt(LocalDateTime.now());
        t.setExpiresAt(expiresAt);
        t.setUsed(used);
        return t;
    }

    @Test
    public void findValidByToken_validToken_returnsToken() {
        String uuid = UUID.randomUUID().toString();
        TerminalSessionToken token = buildToken(uuid, 1L, LocalDateTime.now().plusSeconds(300), false);
        entityManager.persist(token);
        entityManager.flush();

        Optional<TerminalSessionToken> result = repository.findValidByToken(uuid, LocalDateTime.now());

        assertTrue(result.isPresent());
        assertEquals(uuid, result.get().getToken());
        assertFalse(result.get().isUsed());
    }

    @Test
    public void findValidByToken_usedToken_returnsEmpty() {
        String uuid = UUID.randomUUID().toString();
        TerminalSessionToken token = buildToken(uuid, 1L, LocalDateTime.now().plusSeconds(300), true);
        entityManager.persist(token);
        entityManager.flush();

        Optional<TerminalSessionToken> result = repository.findValidByToken(uuid, LocalDateTime.now());

        assertFalse(result.isPresent());
    }

    @Test
    public void findValidByToken_expiredToken_returnsEmpty() {
        String uuid = UUID.randomUUID().toString();
        TerminalSessionToken token = buildToken(uuid, 1L, LocalDateTime.now().minusSeconds(1), false);
        entityManager.persist(token);
        entityManager.flush();

        Optional<TerminalSessionToken> result = repository.findValidByToken(uuid, LocalDateTime.now());

        assertFalse(result.isPresent());
    }

    @Test
    public void findValidByToken_nonExistentToken_returnsEmpty() {
        Optional<TerminalSessionToken> result = repository.findValidByToken(UUID.randomUUID().toString(), LocalDateTime.now());

        assertFalse(result.isPresent());
    }
}
