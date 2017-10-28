package net.warvale.conquest;

import net.warvale.conquest.commands.ForceStartCommand;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.GameState;
import net.warvale.conquest.listeners.SessionListener;
import net.warvale.conquest.maps.MapManager;
import net.warvale.conquest.tasks.CountdownTask;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class Conquest extends JavaPlugin {
    public static Conquest INSTANCE;
    public static Conquest get() {return INSTANCE;}
    public void onEnable(){
        INSTANCE = this;
        if (!new File("maps").exists()) new File("maps").mkdir();


        saveDefaultConfig();

        new MapManager(); // Make a instance of the map manager.

        new Game().setState(GameState.WAITING); // Make a instance of game and set it's state.
        Game.get().setCountdownEnabled(false);
        Game.get().resetCountdown();

        // Map list to console (for debug purposes)
        System.out.println("Maps: "+StringUtils.join(new ArrayList[]{MapManager.get().getMapNames()}));

        //Register stuff
        Bukkit.getServer().getPluginManager().registerEvents(new SessionListener(), this);
        new CountdownTask().runTaskTimer(this,0L,20L);
        getCommand("forcestart").setExecutor(new ForceStartCommand());



    }
    public void onDisable(){

    }
}
