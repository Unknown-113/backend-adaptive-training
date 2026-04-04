package cz.cyberrange.platform.training.adaptive.persistence.repository.training;

import cz.cyberrange.platform.training.adaptive.persistence.entity.training.TrainingDefinitionAccessGuard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Repository for per-user access limits on one training definition.
 */
@Repository
public interface TrainingDefinitionAccessGuardRepository extends JpaRepository<TrainingDefinitionAccessGuard, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TrainingDefinitionAccessGuard> findByParticipantRefIdAndTrainingDefinitionId(Long participantRefId,
                                                                                          Long trainingDefinitionId);
}
