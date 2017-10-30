package net.warvale.conquest.maps;

import org.bukkit.Location;

public class GameMap {
    private String name;
    private String author;

    private double redCoreX;
    private double redCoreY;
    private double redCoreZ;
    private double blueCoreX;
    private double blueCoreY;
    private double blueCoreZ;

    public Location getRedCore() {
        return new Location(MapManager.get().getMapWorld(),redCoreX,redCoreY,redCoreZ);
    }

    public Location getBlueCore() {
        return new Location(MapManager.get().getMapWorld(),blueCoreX,blueCoreY,blueCoreZ);
    }


    public String getAuthor() {
        return author;
    }

    public String getName() {

        return name;
    }


}
