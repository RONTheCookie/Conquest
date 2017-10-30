package net.warvale.conquest.game;

import net.warvale.conquest.maps.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;

public class Game {
    private static Game INSTANCE;
    public static Game get(){return INSTANCE;}
    public Game(){
        INSTANCE = this;
    }
    private GameState state;
    private boolean countdownEnabled;
    private int countdown;
    private CoreSet coreSet;

    public CoreSet getCoreSet() {
        return coreSet;
    }

    public void setCoreSet(CoreSet coreSet) {
        this.coreSet = coreSet;
    }

    public GameState getState() {
        return state;
    }
    public void setState(GameState _state){
        state = _state;
    }
    public void start() {
        Bukkit.getServer().broadcastMessage(ChatColor.RED+"Starting game!");

        // do stuff
        setCountdownEnabled(false);
        resetCountdown();
        setState(GameState.STARTED);
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public boolean isCountdownEnabled() {
        return countdownEnabled;
    }
    public void setCountdownEnabled(boolean _countdownEnabled) {
        countdownEnabled = _countdownEnabled;
    }
    public void resetCountdown(){
        countdown = 11;
    }


    public void end() {
        if ((MapManager.get().getCurrentRotationPosition() + 1) >= MapManager.get().getRotation().size()) {
            MapManager.get().setCurrentRotationPosition(0);
        } else MapManager.get().setCurrentRotationPosition(MapManager.get().getCurrentRotationPosition() + 1);
        try {
            MapManager.get().loadMap(MapManager.get().getRotation().get(MapManager.get().getCurrentRotationPosition()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Core redCore = new Core(Team.RED);
        Core blueCore = new Core(Team.BLUE);
        this.coreSet = new CoreSet(redCore, blueCore);

        // do stuff

        setState(GameState.WAITING);
        this.resetCountdown();
    }
}
