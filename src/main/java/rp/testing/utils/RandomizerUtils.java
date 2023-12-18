package rp.testing.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class RandomizerUtils {

    public static String generateTestFilterName() {
        return "TestFilter-" + UUID.randomUUID();
    }

}
