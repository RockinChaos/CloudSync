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
package bungee.me.RockinChaos.cloudsync;

import bungee.me.RockinChaos.cloudsync.handlers.UpdateHandler;
import bungee.me.RockinChaos.cloudsync.utils.api.MetricsAPI;
import bungee.me.RockinChaos.cloudsync.utils.api.SnapAPI;
import net.md_5.bungee.api.plugin.Plugin;
import bungee.me.RockinChaos.cloudsync.listeners.Chat;
import bungee.me.RockinChaos.cloudsync.listeners.Messages;

public class CloudSync extends Plugin {
	
	private static CloudSync instance;
	public final String PLUGIN_CHANNEL = "plugin:cloudsync";
	
   /**
    * Called when the plugin is loaded.
    * 
    */
    @Override
    public void onLoad() {
    	instance = this;
    }
    
   /**
    * Called when the plugin is enabled.
    * 
    */
	@Override
	@SuppressWarnings("deprecation")
    public void onEnable() {
    	SnapAPI.setLogger(this.getLogger());
    	SnapAPI.setName(this.getDescription().getName());
    	SnapAPI.setVersion(this.getDescription().getVersion());
    	SnapAPI.setServerCount(this.getProxy().getServers().size());
    	SnapAPI.setOnlineCount(this.getProxy().getOnlineCount());
    	SnapAPI.setOnline(this.getProxy().getConfig().isOnlineMode());
    	new MetricsAPI(this, 10829);
    	UpdateHandler.getUpdater(true);
    	this.getProxy().registerChannel(this.PLUGIN_CHANNEL);
    	this.getProxy().getPluginManager().registerListener(this, new Messages());
    	this.getProxy().getPluginManager().registerListener(this, new Chat());
    }
    
   /**
    * Called when the plugin is disabled.
    * 
    */
    @Override
    public void onDisable() {
    	this.getProxy().unregisterChannel(this.PLUGIN_CHANNEL);
    }
    
   /**
    * Gets the static instance of the main class for cloudsync. 
    * Notice: This class is not an actual API class, this is the main class that extends Plugin. 
    *
    * @return CloudSync instance.
    */  	
  	public static CloudSync getInstance() {
  		return instance;
  	}
}