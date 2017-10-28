package net.warvale.conquest.maps;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapManager {
    public static MapManager INSTANCE;
    public static MapManager get() {return INSTANCE;}
    public MapManager() {
        INSTANCE = this;
    }
    public GameMap getMapFromJson(String name) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("maps/"+name+"/maps.json"));

        String raw = StringUtils.join(lines, '\n');
        GameMap gameMap = new Gson().fromJson(raw,GameMap.class);
        return gameMap;
    }
    public ArrayList<String> getMapNames() {
        ArrayList<String> list = new ArrayList<>();
        File[] files = new File("maps").listFiles();
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }
    public GameMap pickRandomMap() throws IOException {
        Random random = new Random();

        String mapname = getMapNames().get(random.nextInt(getMapNames().size()));
        return getMapFromJson(mapname);
    }
    public void loadMap(GameMap gameMap) throws IOException {
        // Get rid of all other worlds
        for (World world : Bukkit.getServer().getWorlds()) {
            if (world.getName().startsWith("conquest_")) {
                Bukkit.getServer().unloadWorld(world, false);
                new File(world.getName()).delete();
            }
        }

        //Start getting the new one in.
        File src = new File("maps/"+gameMap.getName()+"/world");
        File desti = new File("conquest_"+gameMap.getName());
        if (!desti.exists()) desti.mkdir();
        FileUtils.copyDirectory(src,desti);
        World world = Bukkit.getServer().createWorld(new WorldCreator("conquest_"+gameMap.getName()));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());
        }
    }
}
