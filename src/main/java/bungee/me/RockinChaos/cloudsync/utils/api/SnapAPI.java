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
package bungee.me.RockinChaos.cloudsync.utils.api;

import java.util.logging.Logger;

public class SnapAPI {
	
	private static boolean online;
	private static int onlineCount;
	private static int serverCount;
	private static Logger logger;
	private static String version;
	private static String name;


	
   /**
    * Sets the Reference to the Online of CloudSync.
    * 
    * @param log - The Online of CloudSync.
    */
	public static void setOnline(boolean bool) {
		online = bool;
	}
	
   /**
    * Gets the Online of CloudSync.
    * 
    * @return The Online of CloudSync.
    */
	public static boolean getOnline() {
		return online;
	}
	
   /**
    * Sets the Reference to the Online Count of CloudSync.
    * 
    * @param log - The Online Count of CloudSync.
    */
	public static void setOnlineCount(int size) {
		onlineCount = size;
	}
	
   /**
    * Gets the Online Count of CloudSync.
    * 
    * @return The Online Count of CloudSync.
    */
	public static int getOnlineCount() {
		return onlineCount;
	}
	
   /**
    * Sets the Reference to the Server Count of CloudSync.
    * 
    * @param log - The Server Count of CloudSync.
    */
	public static void setServerCount(int size) {
		serverCount = size;
	}
	
   /**
    * Gets the Server Count of CloudSync.
    * 
    * @return The Server Count of CloudSync.
    */
	public static int getServerCount() {
		return serverCount;
	}
	
   /**
    * Sets the Reference to the Root Logger of CloudSync.
    * 
    * @param log - The Root Logger of CloudSync.
    */
	public static void setLogger(Logger log) {
		logger = log;
	}
	
   /**
    * Gets the Root Logger of CloudSync.
    * 
    * @return The Logger of CloudSync.
    */
	public static Logger getLogger() {
		return logger;
	}
	
   /**
    * Sets the Reference to the Version of CloudSync.
    * 
    * @param log - The Version of CloudSync.
    */
	public static void setVersion(String ver) {
		version = ver;
	}
	
   /**
    * Gets the Version of CloudSync.
    * 
    * @return The Version of CloudSync.
    */
	public static String getVersion() {
		return version;
	}
	
   /**
    * Sets the Reference to the Name of CloudSync.
    * 
    * @param log - The Name of CloudSync.
    */
	public static void setName(String str) {
		name = str;
	}
	
   /**
    * Gets the Name of CloudSync.
    * 
    * @return The Name of CloudSync.
    */
	public static String getName() {
		return name;
	}
}