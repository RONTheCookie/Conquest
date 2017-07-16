package net.warvale.conquest.maps;

import net.warvale.conquest.utils.mc.config.LocationUtil;
import net.warvale.conquest.utils.world.LocationObject;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.*;

public class ConquestMap extends GameMap {

    public ConquestMap(String name, String display) {
        super(name, display);
    }

    //spawns
    private LocationObject redSpawn, redSpawnMin, redSpawnMax, blueSpawn, blueSpawnMin, blueSpawnMax;

    //cores
    private LocationObject redCore, blueCore;

    //map center
    private LocationObject mapCenter;

    @SuppressWarnings("unchecked")
    public Map loadSetting(ConfigurationSection configurationSection) {
        Map map = new HashMap<>();

        redSpawn = LocationUtil.fromConfig(configurationSection.getConfigurationSection("spawns.red"));
        map.put("redSpawn", redSpawn);

        redSpawnMin = LocationUtil.fromConfig(configurationSection.getConfigurationSection("regions.spawns.red.min"));
        map.put("redSpawnMin", redSpawnMin);

        redSpawnMax = LocationUtil.fromConfig(configurationSection.getConfigurationSection("regions.spawns.red.max"));
        map.put("redSpawnMax", redSpawnMax);

        blueSpawn = LocationUtil.fromConfig(configurationSection.getConfigurationSection("spawns.blue"));
        map.put("blueSpawn", blueSpawn);

        blueSpawnMin = LocationUtil.fromConfig(configurationSection.getConfigurationSection("regions.spawns.blue.min"));
        map.put("blueSpawnMin", blueSpawnMin);

        blueSpawnMax = LocationUtil.fromConfig(configurationSection.getConfigurationSection("regions.spawns.blue.max"));
        map.put("blueSpawnMax", blueSpawnMax);

        redCore = LocationUtil.fromConfig(configurationSection.getConfigurationSection("cores.red"));
        map.put("redCore", redCore);

        blueCore = LocationUtil.fromConfig(configurationSection.getConfigurationSection("cores.blue"));
        map.put("blueCore", blueCore);

        mapCenter = LocationUtil.fromConfig(configurationSection.getConfigurationSection("center"));

        map.put("center", mapCenter);

        return map;
    }

    public LocationObject getRedSpawn() {
        return (LocationObject) getSettings().get("redSpawn");
    }

    public LocationObject getRedSpawnMin() {
        return (LocationObject) getSettings().get("redSpawnMin");
    }

    public LocationObject getRedSpawnMax() {
        return (LocationObject) getSettings().get("redSpawnMax");
    }

    public LocationObject getBlueSpawn() {
        return (LocationObject) getSettings().get("blueSpawn");
    }

    public LocationObject getBlueSpawnMin() {
        return (LocationObject) getSettings().get("blueSpawnMin");
    }

    public LocationObject getBlueSpawnMax() {
        return (LocationObject) getSettings().get("blueSpawnMax");
    }

    public LocationObject getRedCore() {
        return (LocationObject) getSettings().get("redCore");
    }

    public LocationObject getBlueCore() {
        return (LocationObject) getSettings().get("blueCore");
    }

    public LocationObject getCenter() {
        return (LocationObject) getSettings().get("center");
    }

}
