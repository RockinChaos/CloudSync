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
package me.RockinChaos.cloudsync.bungee.handlers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import me.RockinChaos.cloudsync.bungee.utils.ServerUtils;
import me.RockinChaos.cloudsync.bungee.utils.StringUtils;
import me.RockinChaos.cloudsync.bungee.utils.api.SnapAPI;

public class UpdateHandler {
	private final String destination = "cloudsync";
    private final String HOST = "https://api.github.com/repos/RockinChaos/" + this.destination + "/releases/latest";
    private String versionExact = SnapAPI.getVersion();
    private String localeVersion = this.versionExact.split("-")[0];
    private String latestVersion;
    private boolean betaVersion = this.versionExact.contains("-SNAPSHOT") || this.versionExact.contains("-BETA") || this.versionExact.contains("-ALPHA");
    private boolean devVersion = this.localeVersion.equals("${project.version}");
    
    private static UpdateHandler updater;
        
   /**
    * Initializes the UpdateHandler and Checks for Updates upon initialization.
    *
    */
    public UpdateHandler() {
       this.checkUpdates();
    }
    
    
   /**
    * Checks to see if an update is required, notifying the console window and online op players.
    * 
    * @param sender - The executor of the update checking.
    * @param onStart - If it is checking for updates on start.
    */
    public void checkUpdates() {
    	if (this.updateNeeded()) {
    		if (this.betaVersion) {
    			ServerUtils.logInfo("Your current version: v" + this.localeVersion + "-SNAPSHOT");
    			ServerUtils.logInfo("This SNAPSHOT is outdated and a release version is now available.");
    		} else {
    			ServerUtils.logInfo("Your current version: v" + this.localeVersion + "-RELEASE");
    		}
    		ServerUtils.logInfo("A new version is available: v" + this.latestVersion + "-RELEASE");
    		ServerUtils.logInfo("Get it from: https://github.com/RockinChaos/" + this.destination + "/releases/latest");
    	}
    }
    
   /**
    * Directly checks to see if the GitHub host has an update available.
    * 
    * @param sender - The executor of the update checking.
    * @param onStart - If it is checking for updates on start.
    * @return If an update is needed.
    */
    private boolean updateNeeded() {
    	try {
    		URLConnection connection = new URL(this.HOST + "?_=" + System.currentTimeMillis()).openConnection();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	    String JsonString = StringUtils.toString(reader); 
			JSONObject objectReader = (JSONObject) JSONValue.parseWithException(JsonString);
			String gitVersion = objectReader.get("tag_name").toString();
    		reader.close();
    		if (gitVersion.length() <= 7) {
    			this.latestVersion = gitVersion.replaceAll("[a-z]", "").replace("-SNAPSHOT", "").replace("-BETA", "").replace("-ALPHA", "").replace("-RELEASE", "");
    			String[] latestSplit = this.latestVersion.split("\\.");
    			String[] localeSplit = this.localeVersion.split("\\.");
    			if (this.devVersion) {
    				return false;
    			} else if ((Integer.parseInt(latestSplit[0]) > Integer.parseInt(localeSplit[0]) || Integer.parseInt(latestSplit[1]) > Integer.parseInt(localeSplit[1]) || Integer.parseInt(latestSplit[2]) > Integer.parseInt(localeSplit[2]))
    					|| (this.betaVersion && (Integer.parseInt(latestSplit[0]) == Integer.parseInt(localeSplit[0]) && Integer.parseInt(latestSplit[1]) == Integer.parseInt(localeSplit[1]) && Integer.parseInt(latestSplit[2]) == Integer.parseInt(localeSplit[2])))) {
    				return true;
    			}
    		}
    	} catch (FileNotFoundException e1) {	
    	} catch (Exception e2) {
    		e2.printStackTrace();
    		ServerUtils.logInfo("Failed to check for updates, connection could not be made.");
    		return false;
    	}
    	return false;
    }
    
   /**
    * Gets the instance of the UpdateHandler.
    * 
    * @param regen - If the instance should be regenerated.
    * @return The UpdateHandler instance.
    */
    public static UpdateHandler getUpdater(boolean regen) { 
        if (updater == null || regen) { updater = new UpdateHandler(); }
        return updater; 
    } 
}