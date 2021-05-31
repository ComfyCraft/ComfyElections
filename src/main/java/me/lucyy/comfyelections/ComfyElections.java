package me.lucyy.comfyelections;

import me.lucyy.comfyelections.command.ElectionsRootNode;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.bukkit.BukkitNodeExecutor;
import me.lucyy.squirtgun.format.FormatProvider;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class ComfyElections extends JavaPlugin {

	private final ElectionManager manager = new ElectionManager();
	private final FormatProvider formatter = new Formatter();

	@Override
	public void onEnable() {
		TabExecutor executor = new BukkitNodeExecutor(new ElectionsRootNode(manager), formatter);
		PluginCommand cmd = Objects.requireNonNull(getCommand("elections"), "Elections command missing from plugin yml");

		cmd.setExecutor(executor);
		cmd.setTabCompleter(executor);
	}
}
