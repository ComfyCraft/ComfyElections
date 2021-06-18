package me.lucyy.comfyelections.election;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

public class ElectionManager {

    private final YamlConfiguration configuration;
    private final Path configPath;
    private Set<Election> elections;

    public void save() {
        try {
            configuration.save(configPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Set<Election> newSet = new HashSet<>();
        ConfigurationSection section = configuration.getConfigurationSection("elections");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                newSet.add(configuration.getObject("elections." + key, Election.class));
            }
        }
        elections = newSet;
    }

    public ElectionManager(JavaPlugin plugin) throws IOException {
        this.configPath = new File(plugin.getDataFolder(), "datastore.yml").toPath();

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!Files.exists(configPath)) {
            Files.copy(Objects.requireNonNull(plugin.getResource("datastore.yml")), configPath);
        }
        configuration = YamlConfiguration.loadConfiguration(configPath.toFile());
        load();
    }

    public Election createElection(String name, LocalDateTime finish) {
        Election election = new Election(name, finish);
        elections.add(election);
        save();
        return election;
    }

    public Set<Election> getCurrentElections() {
        return elections;
    }
}
