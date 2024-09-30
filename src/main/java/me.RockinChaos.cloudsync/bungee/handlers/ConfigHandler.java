package me.RockinChaos.cloudsync.bungee.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import me.RockinChaos.cloudsync.bungee.CloudSync;

public class ConfigHandler {
	
	public static void loadConfig() throws IOException {
	    if (!CloudSync.getInstance().getDataFolder().exists()) {
	        CloudSync.getInstance().getDataFolder().mkdir();
	    }
	
	    File file = new File(CloudSync.getInstance().getDataFolder(), "config.yml");
	
	 
	    if (!file.exists()) {
	        try (InputStream in = CloudSync.getInstance().getResourceAsStream("config.yml")) {
	            Files.copy(in, file.toPath());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
