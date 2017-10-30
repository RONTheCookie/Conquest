package net.warvale.conquest.game;

import org.bukkit.Location;

public class Core {
    private Team team;
    private int durability;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Core(Team team){
        this.durability = 20;
        this.team = team;
    }
    public int getDurability() {
        return durability;
    }
    public boolean isDestroyed(){
        return durability < 1;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Team getTeam() {

        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
