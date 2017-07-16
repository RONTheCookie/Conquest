package net.warvale.conquest.maps;

import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.utils.files.FileUtils;
import net.warvale.conquest.utils.world.VoidWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public abstract class GameMap {

    public static List<GameMap> getMaps() {
        return maps;
    }

    public static GameMap getMap(String s) {
        for (GameMap map : getMaps()) {
            if (map.getName().equalsIgnoreCase(s)) {
                return map;
            }
        }
        return null;
    }

    public static void doMaps() {
        try {
            extractMaps();
            setupMaps();
        } catch (Exception e) {
            ConquestCore.get().getLogger().log(Level.WARNING, "Could not setup maps", e);
        }
    }

    public static void doMap(GameMap map){
        try {
            extractMap(map);
            setupMap(map);
        } catch (Exception e) {
            ConquestCore.get().getLogger().log(Level.WARNING, "Could not setup map", e);
        }
    }


    public static void registerMap(GameMap map) {
        getMaps().add(map);
    }

    private static void extractMaps() {
        for (GameMap m : getMaps()) {
            extractMap(m);
        }
    }

    public static void extractMap(GameMap map) {
        File mapto = new File(map.getName());
        if (mapto.exists()) {
            FileUtils.deleteDir(mapto);
        }
        File temp = new File("temp");
        try {
            FileUtils.unZip(map.getZip().getPath(), temp.getPath());
        } catch (IOException e) {
            ConquestCore.get().getLogger().log(Level.WARNING, "Could not extract map", e);
        }
        try {
            FileUtils.copy(new File(temp + File.separator + temp.list()[0]), mapto);
        } catch (IOException e) {
            ConquestCore.get().getLogger().log(Level.WARNING, "Could not copy map", e);
        }
        FileUtils.deleteDir(temp);
    }

    private static void setupMaps() {
        for (GameMap map : getMaps()) {
            setupMap(map);
        }
    }

    public static World setupMap(GameMap map) {
        World world = Bukkit.getWorld(map.getName());
        if (world != null) {
            return world;
        }
        World w = new WorldCreator(map.getName()).generator(VoidWorldGenerator.getGenerator()).generateStructures(false).createWorld();
        w.setAutoSave(false);
        w.setKeepSpawnInMemory(false);
        w.setThundering(false);
        w.setStorm(false);
        w.setDifficulty(Difficulty.NORMAL);
        w.setFullTime(0L);
        return w;
    }

    private static List<GameMap> maps = new ArrayList<>();
    private File yml, zip;
    private String name;
    private String display;
    private String[] authors;
    private Map settings;

    public GameMap(String name, String display) {
        this.name = name;
        this.display = display;
        yml = new File(ConquestCore.getMapDir(), name + ".yml");
        zip = new File(ConquestCore.getMapDir(), name + ".zip");
        settings =  loadSetting(YamlConfiguration.loadConfiguration(yml).getConfigurationSection("settings"));
    }

    public Map getSettings() {
        return settings;
    }

    public abstract Map loadSetting(ConfigurationSection section);

    public String getName() {
        return name;
    }

    public String getDisplay() {
        return display;
    }

    public File getYml() {
        return yml;
    }

    public File getZip() {
        return zip;
    }

    public String[] getAuthors() {
        return authors;
    }

    public World getWorld() {
        return Bukkit.getWorld(name);
    }
}
