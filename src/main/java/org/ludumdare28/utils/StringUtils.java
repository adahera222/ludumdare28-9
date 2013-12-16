package org.ludumdare28.utils;

import java.util.List;
import java.util.Random;

/**
 *
 */
public class StringUtils {

    public static String createRandomName(Random random, final List<String> prefixes, final List<String> postfixes) {
        return pickRandom(prefixes, random) + pickRandom(postfixes, random);
    }

    public static <T> T pickRandom(List<T> items, Random random) {
        final int i = random.nextInt(items.size());
        return items.get(i);
    }

}
