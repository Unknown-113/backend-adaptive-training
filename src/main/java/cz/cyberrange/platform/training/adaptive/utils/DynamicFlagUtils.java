package cz.cyberrange.platform.training.adaptive.utils;

import cz.cyberrange.platform.training.adaptive.persistence.entity.phase.Task;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public final class DynamicFlagUtils {

    private static final String HMAC_SHA_256 = "HmacSHA256";
    private static final String FLAG_PREFIX = "FLAG{";
    private static final String HEX_DIGITS = "0123456789abcdef";

    private DynamicFlagUtils() {
    }

    public static boolean usesDynamicFlag(Task task) {
        return task != null
                && task.isDynamicFlagEnabled()
                && task.getDynamicFlagIntervalMinutes() != null
                && task.getDynamicFlagIntervalMinutes() > 0
                && StringUtils.isNotBlank(task.getDynamicFlagSecret());
    }

    public static String resolveExpectedAnswer(Task task) {
        return resolveExpectedAnswer(task, Instant.now());
    }

    public static String resolveExpectedAnswer(Task task, Instant instant) {
        if (usesDynamicFlag(task)) {
            return generateDynamicFlag(task.getDynamicFlagSecret(), task.getDynamicFlagIntervalMinutes(), instant);
        }
        return task.getAnswer();
    }

    public static String generateDynamicFlag(String secret, int intervalMinutes, Instant instant) {
        long slot = instant.getEpochSecond() / (intervalMinutes * 60L);
        byte[] digest = hmacSha256(secret, Long.toString(slot));
        return FLAG_PREFIX + toHex(digest).substring(0, 16) + "}";
    }

    private static byte[] hmacSha256(String secret, String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA_256);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256));
            return mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to generate dynamic flag.", exception);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte current : bytes) {
            builder.append(HEX_DIGITS.charAt((current >> 4) & 0xF));
            builder.append(HEX_DIGITS.charAt(current & 0xF));
        }
        return builder.toString();
    }
}
