package cz.cyberrange.platform.training.adaptive.service.training;

import cz.cyberrange.platform.training.adaptive.definition.exceptions.ForbiddenException;
import cz.cyberrange.platform.training.adaptive.persistence.entity.training.TrainingDefinitionAccessGuard;
import cz.cyberrange.platform.training.adaptive.persistence.repository.training.TrainingDefinitionAccessGuardRepository;
import cz.cyberrange.platform.training.adaptive.rest.facade.annotations.transaction.TransactionalWO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Guards adaptive training access against repeated requests from the same participant.
 */
@Service
public class TrainingDefinitionAccessGuardService {

    private final TrainingDefinitionAccessGuardRepository trainingDefinitionAccessGuardRepository;
    private final int maxAccessAttemptsPerDefinition;

    public TrainingDefinitionAccessGuardService(TrainingDefinitionAccessGuardRepository trainingDefinitionAccessGuardRepository,
                                                @Value("${training.access.max-attempts-per-definition:10}") int maxAccessAttemptsPerDefinition) {
        this.trainingDefinitionAccessGuardRepository = trainingDefinitionAccessGuardRepository;
        this.maxAccessAttemptsPerDefinition = maxAccessAttemptsPerDefinition;
    }

    @TransactionalWO(propagation = Propagation.REQUIRES_NEW)
    public void checkAndRegisterAccess(Long participantRefId, Long trainingDefinitionId) {
        TrainingDefinitionAccessGuard accessGuard = trainingDefinitionAccessGuardRepository
                .findByParticipantRefIdAndTrainingDefinitionId(participantRefId, trainingDefinitionId)
                .orElseGet(() -> new TrainingDefinitionAccessGuard(
                        participantRefId,
                        trainingDefinitionId,
                        0,
                        false,
                        null,
                        LocalDateTime.now(Clock.systemUTC())
                ));

        if (accessGuard.isBanned()) {
            throw new ForbiddenException("Access to this training definition has been blocked for this participant.");
        }

        accessGuard.setAccessCount(accessGuard.getAccessCount() + 1);
        accessGuard.setLastAccessAt(LocalDateTime.now(Clock.systemUTC()));

        if (accessGuard.getAccessCount() > maxAccessAttemptsPerDefinition) {
            accessGuard.setBanned(true);
            accessGuard.setBannedAt(LocalDateTime.now(Clock.systemUTC()));
            trainingDefinitionAccessGuardRepository.saveAndFlush(accessGuard);
            throw new ForbiddenException("Access limit for this training definition has been exceeded.");
        }

        trainingDefinitionAccessGuardRepository.saveAndFlush(accessGuard);
    }
}
