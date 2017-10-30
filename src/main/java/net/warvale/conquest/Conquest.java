package net.warvale.conquest;

import net.warvale.conquest.commands.ForceEndCommand;
import net.warvale.conquest.commands.ForceStartCommand;
import net.warvale.conquest.commands.GameStateCommand;
import net.warvale.conquest.commands.MapsCommand;
import net.warvale.conquest.game.*;
import net.warvale.conquest.listeners.SessionListener;
import net.warvale.conquest.maps.GameMap;
import net.warvale.conquest.maps.MapManager;
import net.warvale.conquest.tasks.CountdownTask;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Conquest extends JavaPlugin {
    public static Conquest INSTANCE;
    public static Conquest get() {return INSTANCE;}
    public void onEnable(){
        INSTANCE = this;
        if (!new File("maps").exists()) new File("maps").mkdir();


        saveDefaultConfig();

        new MapManager(); // Make a instance of the map manager.
        try {
        ArrayList<GameMap> rotation = new ArrayList<>();
        for (String mapname : MapManager.get().getMapNames()) {
            rotation.add(MapManager.get().getMapFromJson(mapname));
        }
        MapManager.get().setRotation(rotation);
        MapManager.get().setCurrentRotationPosition(0);
            } catch (IOException e) {
            e.printStackTrace();
        }

        new Game().setState(GameState.WAITING); // Make a instance of game and set it's state.
        Game.get().setCountdownEnabled(false);
        Game.get().resetCountdown();
        Core redCore = new Core(Team.RED);
        Core blueCore = new Core(Team.BLUE);
        Game.get().setCoreSet(new CoreSet(redCore, blueCore));

        // Map list to console (for debug purposes)
        System.out.println("Maps: "+StringUtils.join(new ArrayList[]{MapManager.get().getMapNames()}));

        //Register stuff
        Bukkit.getServer().getPluginManager().registerEvents(new SessionListener(), this);
        new CountdownTask().runTaskTimer(this,0L,20L);
        getCommand("forcestart").setExecutor(new ForceStartCommand());
        getCommand("forceend").setExecutor(new ForceEndCommand());
        getCommand("gamestate").setExecutor(new GameStateCommand());
        getCommand("maps").setExecutor(new MapsCommand());
        try {
            MapManager.get().loadMap(MapManager.get().getRotation().get(MapManager.get().getCurrentRotationPosition()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void onDisable(){

    }
}
