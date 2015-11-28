package main.java.com.trophonix.eztp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		init();
		System.out.println("[EZTP] Successfully Initialized!");
	}
	
	public void onDisable() {
		
	}
	
	public void init() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlaceSign(SignChangeEvent e) {
		if (!(e.getLine(0).equalsIgnoreCase("[eztp]"))) {
			return;
		}
		
		if (!(e.getPlayer().hasPermission("eztp.sign.make"))) {
			e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to make teleport signs!");
			return;
		}
		
		
		
		try {
			double[] coords = {
				Double.parseDouble(e.getLine(1).replace(" ", "").split(",")[0]),
				Double.parseDouble(e.getLine(1).replace(" ", "").split(",")[1]),
				Double.parseDouble(e.getLine(1).replace(" ", "").split(",")[2])
			};
			
			e.setLine(0, ChatColor.BLUE + "[EzTP]");
		} catch (Exception ex) {
			e.getPlayer().sendMessage(ChatColor.RED + "Make sure lines 2 through 4 are valid numbers!");
			return;
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if ((!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) || (!(e.getClickedBlock().getState() instanceof Sign))) {
			return;
		}
		Sign sign = (Sign) e.getClickedBlock().getState();
		
		double[] coords = {
				Double.parseDouble(sign.getLine(1).replace(" ", "").split(",")[0]),
				Double.parseDouble(sign.getLine(1).replace(" ", "").split(",")[1]),
				Double.parseDouble(sign.getLine(1).replace(" ", "").split(",")[2])
			};
		
		if (!sign.getLine(3).isEmpty()) {
			if (!(e.getPlayer().hasPermission("eztp.location." + sign.getLine(3)))) {
				e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission for this location!");
				return;
			}
		}
		
		if (sign.getLine(0).equals(ChatColor.BLUE + "[EzTP]")) {
			e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), coords[0], coords[1], coords[2], e.getPlayer().getLocation().getYaw(), e.getPlayer().getLocation().getPitch()));
		}
	}
	
}
