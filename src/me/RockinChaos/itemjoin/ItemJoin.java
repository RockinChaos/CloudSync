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
package me.RockinChaos.itemjoin;

import me.RockinChaos.itemjoin.listeners.Messages;
import me.RockinChaos.itemjoin.utils.api.MetricsAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class ItemJoin extends Plugin {
	
	private static ItemJoin instance;
	public final String PLUGIN_CHANNEL = "plugin:itemjoin";
	
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
    public void onEnable() {
    	new MetricsAPI(this, 10829);
    	this.getProxy().registerChannel(this.PLUGIN_CHANNEL);
    	this.getProxy().getPluginManager().registerListener(this, new Messages());
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
    * Gets the static instance of the main class for ItemJoin. 
    * Notice: This class is not an actual API class, this is the main class that extends Plugin. 
    *
    * @return ItemJoin instance.
    */  	
  	public static ItemJoin getInstance() {
  		return instance;
  	}
}