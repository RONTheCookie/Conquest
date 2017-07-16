package net.warvale.conquest.game;

import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.config.ConfigManager;
import net.warvale.conquest.maps.ConquestMap;
import net.warvale.conquest.maps.GameMap;

public class Game {

    private static Game instance;
    private State state;
    private int maxPlayer;
    private int minPlayers;
    private int gameLength;
    private GameMap chosenmap;
    private boolean stats;
    private boolean debug;

    public static Game get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void setup() {
        try {
            state = State.valueOf(ConfigManager.getConfig().getString("state"));
        } catch (Exception e) {
            ConquestCore.get().getLogger().warning("Setting the state to LOBBY as it can't find the saved one!");
            state = State.LOBBY;
        }

        maxPlayer = ConfigManager.getConfig().getInt("maxPlayers", 50);
        minPlayers = ConfigManager.getConfig().getInt("minPlayers", 8);
        gameLength = ConfigManager.getConfig().getInt("gameLength", 60);
        stats = ConfigManager.getConfig().getBoolean("stats", true);
        debug = ConfigManager.getConfig().getBoolean("debug", false);
    }

    /**
     * Set the current state.
     *
     * @param state The new state.
     */
    public void setState(State state) {
        this.state = state;

        ConfigManager.getConfig().set("state", state.name());
        ConfigManager.getInstance().saveConfig();
    }

    /**
     * Check if the current state is the same as the given state.
     *
     * @param state The state checking.
     * @return True if it's the same, false otherwise.
     */
    public boolean isState(State state) {
        return getState() == state;
    }

    /**
     * Gets the current game state.
     *
     * @return The current state.
     */
    public State getState() {
        return state;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setChosenmap(GameMap map) {
        this.chosenmap = map;
    }

    public GameMap getChosenMap() {
        return chosenmap;
    }

    public boolean isStatsEnabled() {
        return this.stats;
    }

    public void setStats(boolean enable) {
        this.stats = stats;

        ConfigManager.getConfig().set("stats", enable);
        ConfigManager.getInstance().saveConfig();
    }

    public boolean isDebugEnabled() {
        return this.debug;
    }

    public void setDebug(boolean enable) {
        this.debug = enable;

        ConfigManager.getConfig().set("debug", enable);
        ConfigManager.getInstance().saveConfig();
    }



}
