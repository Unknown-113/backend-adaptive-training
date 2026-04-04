package cz.cyberrange.platform.training.adaptive.service.training;

import cz.cyberrange.platform.training.adaptive.definition.exceptions.ForbiddenException;
import cz.cyberrange.platform.training.adaptive.persistence.entity.training.TrainingDefinitionAccessGuard;
import cz.cyberrange.platform.training.adaptive.persistence.repository.training.TrainingDefinitionAccessGuardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class TrainingDefinitionAccessGuardServiceTest {

    @Mock
    private TrainingDefinitionAccessGuardRepository trainingDefinitionAccessGuardRepository;

    private TrainingDefinitionAccessGuardService trainingDefinitionAccessGuardService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        trainingDefinitionAccessGuardService = new TrainingDefinitionAccessGuardService(trainingDefinitionAccessGuardRepository, 10);
    }

    @Test
    void shouldStoreAccessBelowLimit() {
        given(trainingDefinitionAccessGuardRepository.findByParticipantRefIdAndTrainingDefinitionId(10L, 20L))
                .willReturn(Optional.empty());
        given(trainingDefinitionAccessGuardRepository.saveAndFlush(any(TrainingDefinitionAccessGuard.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        trainingDefinitionAccessGuardService.checkAndRegisterAccess(10L, 20L);

        ArgumentCaptor<TrainingDefinitionAccessGuard> captor = ArgumentCaptor.forClass(TrainingDefinitionAccessGuard.class);
        verify(trainingDefinitionAccessGuardRepository).saveAndFlush(captor.capture());
        TrainingDefinitionAccessGuard savedGuard = captor.getValue();
        assertEquals(10L, savedGuard.getParticipantRefId());
        assertEquals(20L, savedGuard.getTrainingDefinitionId());
        assertEquals(1, savedGuard.getAccessCount());
        assertFalse(savedGuard.isBanned());
        assertNotNull(savedGuard.getLastAccessAt());
    }

    @Test
    void shouldBanParticipantWhenAccessLimitIsExceeded() {
        TrainingDefinitionAccessGuard accessGuard = new TrainingDefinitionAccessGuard(10L, 20L, 10, false, null,
                LocalDateTime.now().minusMinutes(1));
        given(trainingDefinitionAccessGuardRepository.findByParticipantRefIdAndTrainingDefinitionId(10L, 20L))
                .willReturn(Optional.of(accessGuard));
        given(trainingDefinitionAccessGuardRepository.saveAndFlush(any(TrainingDefinitionAccessGuard.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ForbiddenException forbiddenException = assertThrows(
                ForbiddenException.class,
                () -> trainingDefinitionAccessGuardService.checkAndRegisterAccess(10L, 20L)
        );

        assertEquals("Access limit for this training definition has been exceeded.", forbiddenException.getMessage());
        assertEquals(11, accessGuard.getAccessCount());
        assertTrue(accessGuard.isBanned());
        assertNotNull(accessGuard.getBannedAt());
        verify(trainingDefinitionAccessGuardRepository).saveAndFlush(accessGuard);
    }

    @Test
    void shouldRejectAlreadyBannedParticipantWithoutSaving() {
        TrainingDefinitionAccessGuard accessGuard = new TrainingDefinitionAccessGuard(10L, 20L, 11, true,
                LocalDateTime.now().minusMinutes(2), LocalDateTime.now().minusMinutes(1));
        given(trainingDefinitionAccessGuardRepository.findByParticipantRefIdAndTrainingDefinitionId(10L, 20L))
                .willReturn(Optional.of(accessGuard));

        ForbiddenException forbiddenException = assertThrows(
                ForbiddenException.class,
                () -> trainingDefinitionAccessGuardService.checkAndRegisterAccess(10L, 20L)
        );

        assertEquals("Access to this training definition has been blocked for this participant.", forbiddenException.getMessage());
    }
}
