package net.warvale.conquest;

import net.warvale.conquest.commands.CommandHandler;
import net.warvale.conquest.config.ConfigManager;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.game.scoreboards.GameScoreboard;
import net.warvale.conquest.game.scoreboards.LobbyScoreboard;
import net.warvale.conquest.game.teams.TeamManager;
import net.warvale.conquest.listeners.MenuListener;
import net.warvale.conquest.listeners.PingListener;
import net.warvale.conquest.listeners.SessionListener;
import net.warvale.conquest.listeners.WeatherListener;
import net.warvale.conquest.maps.ConquestMap;
import net.warvale.conquest.maps.GameMap;
import net.warvale.conquest.maps.VoteMenu;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.spec.SpecManager;
import net.warvale.conquest.tasks.LobbyTask;
import net.warvale.conquest.tasks.ScoreboardTask;
import net.warvale.conquest.utils.mc.items.EnchantGlow;
import net.warvale.conquest.utils.mc.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ConquestCore extends JavaPlugin {

    private static ConquestCore instance;
    private static final Random random = new Random();

    //map dir
    private static File mapDir = new File("Maps");

    //menus
    private Set<Menu> registeredMenus = new HashSet<>();

    //commands
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {

        //setup config manger
        ConfigManager.getInstance().setup();

        //setup message manager
        MessageManager.getInstance().setup();

        //setup the game
        Game.get().setup();
        Game.get().setState(State.LOBBY);

        //setup teams
        TeamManager.get().setup();

        //load the maps
        GameMap.registerMap(new ConquestMap("Redwood_Forest", "Redwood Forest"));
        GameMap.registerMap(new ConquestMap("Pagoda_Everglade", "Pagoda Everglade"));
        GameMap.registerMap(new ConquestMap("Volcano_Island", "Volcano Island"));
        GameMap.registerMap(new ConquestMap("Extraterrestrial", "Extraterrestrial"));
        GameMap.registerMap(new ConquestMap("Canyon_Brook", "Canyon Brook"));
        GameMap.doMaps();

        //register menus
        EnchantGlow.getGlow();

        //register any listeners we need
        registerListener(new MenuListener());
        registerListener(new PingListener());
        registerListener(new SessionListener());
        registerListener(new WeatherListener());

        //register commands
        commandHandler = new CommandHandler(this);
        commandHandler.registerCommands();

        //finally register any tasks
        ScoreboardTask.getInstance().runTaskTimer(this, 0, 20);
        LobbyTask.getInstance().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onLoad() {

        instance = this;

        if (!mapDir.exists()) {
            mapDir.mkdir();
        }

    }

    @Override
    public void onDisable() {

        TeamManager.get().getRedTeam().clear();
        TeamManager.get().getBlueTeam().clear();
        SpecManager.get().getSpectators().clear();

        LobbyScoreboard.get().shutdown();
        GameScoreboard.get().shutdown();

    }

    public static ConquestCore get() {
        return instance;
    }

    public static Random getRandom() {
        return random;
    }

    public void registerMenu(Menu menu) {
        if (registeredMenus.contains(menu)) {
            throw new IllegalArgumentException("Menu already registered");
        }
        registeredMenus.add(menu);
    }

    public void unregisterMenu(Menu menu) {
        if (registeredMenus.contains(menu)) {
            registeredMenus.remove(menu);
        } else {
            throw new IllegalArgumentException("Menu not registered");
        }
    }

    public void unregisterMenus() {
        registeredMenus.clear();
    }

    public Set<Menu> listMenu() {
        return new HashSet<>(registeredMenus);
    }

    public static void registerListener(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, instance);
    }

    public static File getMapDir() {
        return mapDir;
    }

}
