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
package me.RockinChaos.cloudsync.bungee.utils;

import me.RockinChaos.cloudsync.bungee.utils.api.SnapAPI;

public class ServerUtils {
	
   /**
    * Sends a low priority log message as the plugin header.
    * 
    * @param message - The unformatted message text to be sent.
    */
	public static void logInfo(String message) {
		String prefix = "[CloudSync] ";
		message = prefix + message;
		if (message.equalsIgnoreCase("") || message.isEmpty()) { message = ""; }
		SnapAPI.getLogger().info(message);
	}
	
   /**
    * Sends a warning message as the plugin header.
    * 
    * @param message - The unformatted message text to be sent.
    */
	public static void logWarn(String message) {
		String prefix = "[CloudSync_WARN] ";
		message = prefix + message;
		if (message.equalsIgnoreCase("") || message.isEmpty()) { message = ""; }
		SnapAPI.getLogger().warning(message);
	}
	
   /**
    * Sends a developer warning message as the plugin header.
    * 
    * @param message - The unformatted message text to be sent.
    */
	public static void logDev(String message) {
		String prefix = "[CloudSync_DEVELOPER] ";
		message = prefix + message;
		if (message.equalsIgnoreCase("") || message.isEmpty()) { message = ""; }
		SnapAPI.getLogger().warning(message);
	}
	
   /**
    * Sends a error message as the plugin header.
    * 
    * @param message - The unformatted message text to be sent.
    */
	public static void logSevere(String message) {
		String prefix = "[CloudSync_ERROR] ";
		if (message.equalsIgnoreCase("") || message.isEmpty()) { message = ""; }
		SnapAPI.getLogger().severe(prefix + message);
	}
	
   /**
    * Sends the StackTrace of an Exception if it is Severe.
    * 
    * @param e - The exception to be sent.
    */
	public static void sendSevereTrace(final Exception e) {
		e.printStackTrace();
	}
}