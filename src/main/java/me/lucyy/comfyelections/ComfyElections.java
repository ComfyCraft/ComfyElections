package me.lucyy.comfyelections;

import me.lucyy.comfyelections.command.*;
import me.lucyy.comfyelections.election.ElectionManager;
import me.lucyy.squirtgun.bukkit.BukkitNodeExecutor;
import me.lucyy.squirtgun.bukkit.BukkitPlatform;
import me.lucyy.squirtgun.command.node.subcommand.SubcommandNode;
import me.lucyy.squirtgun.format.FormatProvider;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class ComfyElections extends JavaPlugin {

    private final FormatProvider formatter = new Formatter();

    @Override
    public void onEnable() {
        ElectionManager manager;
        try {
            manager = new ElectionManager(this);
        } catch (IOException e) {
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
            return;
        }

        BukkitPlatform platform = new BukkitPlatform(this);
        TabExecutor executor = new BukkitNodeExecutor(
                SubcommandNode.withFallback("elections", "TODO", null,
                        new ListElectionsNode(manager),
                        new CreateElectionNode(manager),
                        new RegisterForElectionNode(manager),
                        new VoteNode(manager),
                        new ElectionInfoNode(manager, platform)),
                formatter, platform);
        PluginCommand cmd = Objects.requireNonNull(getCommand("elections"),
				"Elections command missing from plugin yml");

        cmd.setExecutor(executor);
        cmd.setTabCompleter(executor);
    }
}
