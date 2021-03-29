/*
 * ItemJoin-Bungee
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
package me.RockinChaos.itemjoin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class StringUtils {
    
   /**
    * Checks if string1 contains string2.
    * 
    * @param string1 - The String to be checked if it contains string2.
    * @param string2 - The String that should be inside string1.
    * @return If string1 contains string2.
    */
	public static boolean containsIgnoreCase(final String string1, final String string2) {
		if (string1 != null && string2 != null && string1.toLowerCase().contains(string2.toLowerCase())) {
			return true;
		}
		return false;
	}
	
   /**
    * Checks if string1 contains string2.
    * 
    * @param string1 - The String to be checked if it contains string2.
    * @param string2 - The String that should be inside string1.
    * @param argument - The argument to be split between the string.
    * @return If string1 contains string2.
    */
	public static boolean splitIgnoreCase(final String string1, final String string2, final String argument) {
		String[] parts = string1.split(argument);
		boolean splitParts = string1.contains(argument);
		for (int i = 0; i < (splitParts ? parts.length : 1); i++) {
			if ((splitParts && parts[i] != null && string2 != null && parts[i].toLowerCase().replace(" ", "").equalsIgnoreCase(string2.replace(" ", "").toLowerCase()))
			|| (!splitParts && string1 != null && string2 != null && string1.toLowerCase().equalsIgnoreCase(string2.toLowerCase()))) {
				return true;
			}
		}
		return false;
	}
	
   /**
    * Checks if the List contains the String.
    * 
    * @param list - The List to be checked if it contains the String.
    * @param str - The String that should be inside the List.
    * @return If the List contained the String.
    */
	public static boolean containsValue(final List<?> list, final String str) {
		boolean bool = false;
		for (Object l : list) { if (l.toString().equalsIgnoreCase(str)) { bool = true; break; } }
		return bool;
	}
	
   /**
    * Converts a BufferedReader to a String output.
    * 
    * @param reader - the BufferedReader to be converted.
    * @return The resulting appended String.
    */
	public static String toString(BufferedReader reader) throws IOException {
		StringBuilder result = new StringBuilder();
		String line = null; while ((line = reader.readLine()) != null) { result.append(line); }
		return result.toString();
	}
}