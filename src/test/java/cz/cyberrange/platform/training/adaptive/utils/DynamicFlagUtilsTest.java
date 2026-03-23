package cz.cyberrange.platform.training.adaptive.utils;

import cz.cyberrange.platform.training.adaptive.persistence.entity.phase.Task;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DynamicFlagUtilsTest {

    @Test
    void shouldGenerateExpectedFlagForKnownSlot() {
        String flag = DynamicFlagUtils.generateDynamicFlag("secret", 15, Instant.ofEpochSecond(1700000000L));

        assertEquals("FLAG{b1175df5ddb31b8c}", flag);
    }

    @Test
    void shouldResolveDynamicAnswerFromTaskConfiguration() {
        Task task = new Task();
        task.setDynamicFlagEnabled(true);
        task.setDynamicFlagIntervalMinutes(15);
        task.setDynamicFlagSecret("secret");

        String flag = DynamicFlagUtils.resolveExpectedAnswer(task, Instant.ofEpochSecond(1700000000L));

        assertEquals("FLAG{b1175df5ddb31b8c}", flag);
    }

    @Test
    void shouldRotateFlagAcrossIntervals() {
        String first = DynamicFlagUtils.generateDynamicFlag("secret", 15, Instant.ofEpochSecond(1700000000L));
        String second = DynamicFlagUtils.generateDynamicFlag("secret", 15, Instant.ofEpochSecond(1700000900L));

        assertNotEquals(first, second);
    }
}
