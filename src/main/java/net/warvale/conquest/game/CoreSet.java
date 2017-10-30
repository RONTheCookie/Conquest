package net.warvale.conquest.game;

public class CoreSet {
    private Core redCore;
    private Core blueCore;
    public CoreSet(Core redCore, Core blueCore) {
        this.redCore = redCore;
        this.blueCore = blueCore;
    }
    public Core getBlueCore() {
        return blueCore;
    }

    public void setBlueCore(Core blueCore) {
        this.blueCore = blueCore;
    }

    public Core getRedCore() {

        return redCore;
    }

    public void setRedCore(Core redCore) {
        this.redCore = redCore;
    }
}
