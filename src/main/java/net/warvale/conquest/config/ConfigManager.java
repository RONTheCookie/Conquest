package net.warvale.conquest.config;

import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.utils.files.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    //config
    private static FileConfiguration config;
    private File configFile;

    //lobby
    private static FileConfiguration lobby;
    private File lobbyFile;

    //messages
    private static FileConfiguration messages;
    private File messageFile;

    public void setup() {

        //copy default config file
        ConquestCore.get().getLogger().info("Loading configs");

        try {

            if (!ConquestCore.get().getDataFolder().exists()) {
                ConquestCore.get().getDataFolder().mkdir();
            }

            configFile = new File(ConquestCore.get().getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                //copy default config file and defaults
                FileUtils.loadFile("config.yml");
            }

            lobbyFile = new File(ConquestCore.get().getDataFolder(), "lobby.yml");
            if (!lobbyFile.exists()) {
                //save default lobby settings
                FileUtils.loadFile("lobby.yml");
            }

            messageFile = new File(ConquestCore.get().getDataFolder(), "messages.yml");
            if (!messageFile.exists()) {
                //save default kits file
                FileUtils.loadFile("messages.yml");
            }

            reloadConfig();
            reloadLobby();
            reloadMessages();

        } catch (Exception ex) {
            ex.printStackTrace();
            ConquestCore.get().getLogger().severe("Could not load configs");
        }

    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getLobby() {
        return lobby;
    }

    public static FileConfiguration getMessages() {
        return messages;
    }

    public void reloadConfig() {
        try {

            config = new YamlConfiguration();
            config.load(configFile);

        } catch (Exception ex) {
            ConquestCore.get().getLogger().severe("Could not reload config: " + configFile.getName());
        }
    }

    public void reloadLobby() {
        try {

            lobby = new YamlConfiguration();
            lobby.load(lobbyFile);

        } catch (Exception ex) {
            ConquestCore.get().getLogger().severe("Could not reload config: " + lobbyFile.getName());
        }
    }

    public void reloadMessages() {
        try {

            messages = new YamlConfiguration();
            messages.load(messageFile);

        } catch (Exception ex) {
            ConquestCore.get().getLogger().severe("Could not reload config: " + messageFile.getName());
        }
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception ex) {
            ConquestCore.get().getLogger().severe("Could not save config: " + configFile.getName());
        }
    }

    public void saveLobby() {
        try {
            lobby.save(lobbyFile);
        } catch (Exception ex) {
            ConquestCore.get().getLogger().severe("Could not save config: " + lobbyFile.getName());
        }
    }

    public void saveMessages() {
        try {
            messages.save(messageFile);
        } catch (Exception ex) {
            ConquestCore.get().getLogger().severe("Could not save config: " + messageFile.getName());
        }
    }

}
