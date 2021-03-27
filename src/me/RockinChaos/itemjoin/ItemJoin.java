package me.RockinChaos.itemjoin;

import me.RockinChaos.itemjoin.listeners.PluginMessage;
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
    	this.getProxy().registerChannel(PLUGIN_CHANNEL);
    	this.getProxy().getPluginManager().registerListener(this, new PluginMessage());
    }
    
   /**
    * Called when the plugin is disabled.
    * 
    */
    @Override
    public void onDisable() {
    	this.getProxy().unregisterChannel(PLUGIN_CHANNEL);
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