package net.warvale.conquest.game;

public class Game {
    private static Game INSTANCE;
    public static Game get(){return INSTANCE;}
    public Game(){
        INSTANCE = this;
    }
    private GameState state;

    public GameState getState() {
        return state;
    }
    public void setState(GameState _state){
        state = _state;
    }
}
