/*
 * CloudSync
 * Copyright (C) CraftationGaming <https://www.craftationgaming.com/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.RockinChaos.cloudsync.snap.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StringUtils {

    /**
     * Checks if the specified String is an Integer Value.
     *
     * @param str The String to be checked.
     * @return If the String is an Integer Value.
     */
    public static boolean isInt(final @Nullable String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Gets a random Integer between the upper and lower limits.
     *
     * @param lower The lower limit.
     * @param upper The upper limit.
     * @return The randomly selected Integer between the limits.
     */
    public static int getRandom(final int lower, final int upper) {
        return new Random().nextInt((upper - lower) + 1) + lower;
    }

    /**
     * Checks if string1 contains string2.
     *
     * @param string1 The String to be checked if it contains string2.
     * @param string2 The String that should be inside string1.
     * @return If string1 contains string2.
     */
    public static boolean containsIgnoreCase(final String string1, final String string2) {
        return string1 != null && string2 != null && string1.toLowerCase().contains(string2.toLowerCase());
    }

    /**
     * Checks if string1 contains string2.
     *
     * @param string1  The String to be checked if it contains string2.
     * @param string2  The String that should be inside string1.
     * @param argument The argument to be split between the string.
     * @return If string1 contains string2.
     */
    public static boolean splitIgnoreCase(final String string1, final String string2, final String argument) {
        String[] parts = string1.split(argument);
        boolean splitParts = string1.contains(argument);
        for (int i = 0; i < (splitParts ? parts.length : 1); i++) {
            if (splitParts && parts[i] != null && string2 != null && parts[i].toLowerCase().replace(" ", "").equalsIgnoreCase(string2.replace(" ", "").toLowerCase()) || !splitParts && string1.equalsIgnoreCase(string2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a Object value to a Map<String, Object>.
     *
     * @param value the Object to be converted to a HashMap.
     * @return The newly create Map<String, Object>.
     */
    public static @Nullable Map<String, Object> toStringMap(@Nonnull Object value) {
        if (value instanceof Map<?, ?>) {
            final Map<?, ?> map = (Map<?, ?>) value;
            final Map<String, Object> typedMap = new HashMap<>();
            for (final Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey() instanceof String) {
                    typedMap.put((String) entry.getKey(), entry.getValue());
                } else {
                    return null;
                }
            }
            return typedMap;
        }
        return null;
    }

    /**
     * Converts a BufferedReader to a String output.
     *
     * @param reader the BufferedReader to be converted.
     * @return The resulting appended String.
     */
    public static String toString(final @Nonnull BufferedReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}