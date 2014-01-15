package netchat.nhadobas;

import java.util.logging.Level;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.Listener;

public class NetMain extends Plugin implements Listener
{
	private static NetMain noodles;
	public static ServerInfo hub;
	@Override
	public void onEnable()
	{
		NetMain.noodles = this;
		this.getProxy().getLogger().log(Level.INFO, "NetChat may or may not enable!");
		new NetChat(this);
		this.getProxy().getLogger().log(Level.INFO, "NetChat is enabled!");
	}
	
	public void onDisable(){
		this.getProxy().getLogger().log(Level.INFO, "NetChat is disabling!");
	}
	
	public static NetMain getPlugin()
	{
		return NetMain.noodles;
	}
	
}
