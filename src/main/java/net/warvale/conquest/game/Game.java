package net.warvale.conquest.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Game {
    private static Game INSTANCE;
    public static Game get(){return INSTANCE;}
    public Game(){
        INSTANCE = this;
    }
    private GameState state;
    private boolean countdownEnabled;
    private int countdown;

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


        // do stuff

        setState(GameState.WAITING);
    }
}
