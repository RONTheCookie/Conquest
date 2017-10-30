package net.warvale.conquest.maps;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapManager {
    public static MapManager INSTANCE;
    public static MapManager get() {return INSTANCE;}
    private GameMap currentMap;
    private World mapWorld;
    private ArrayList<GameMap> rotation;
    private int currentRotationPosition;

    public int getCurrentRotationPosition() {
        return currentRotationPosition;
    }

    public void setCurrentRotationPosition(int currentRotationPosition) {
        this.currentRotationPosition = currentRotationPosition;
    }

    public ArrayList<GameMap> getRotation() {
        return rotation;
    }

    public void setRotation(ArrayList<GameMap> rotation) {
        this.rotation = rotation;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }




    public MapManager() {
        INSTANCE = this;
    }
    public GameMap getMapFromJson(String name) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("maps/"+name+"/map.json"));

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

    public World getMapWorld() {
        return mapWorld;
    }

    public void loadMap(GameMap gameMap) throws IOException {
        currentMap = gameMap;



        //Start getting the new one in.
        File src = new File("maps/"+gameMap.getName()+"/world");
        File desti = new File("conquest_"+gameMap.getName());
        if (!desti.exists()) desti.mkdir();
        FileUtils.copyDirectory(src,desti);
        World world = Bukkit.getServer().createWorld(new WorldCreator("conquest_"+gameMap.getName()));
        mapWorld = world;
        world.setGameRuleValue("doMobSpawning","false");
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD+"You are now playing "+ ChatColor.RED+gameMap.getName()+ChatColor.GOLD+" by "+ChatColor.RED + gameMap.getAuthor()+ChatColor.GOLD+".");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());
        }


        // Get rid of all other worlds
        for (World wworld : Bukkit.getServer().getWorlds()) {

            if (wworld.getName().startsWith("conquest_") && getMapWorld().getName().equalsIgnoreCase(wworld.getName()) ) {
                Bukkit.getServer().unloadWorld(wworld, false);
            }
        }




    }
}
