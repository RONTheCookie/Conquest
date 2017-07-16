package net.warvale.conquest.utils.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import java.util.HashMap;
import java.util.Map;

public class LocationObject implements Cloneable, ConfigurationSerializable {
    public static LocationObject deserialize(Map<String, Object> args) {
        return new LocationObject(NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")));
    }

    private double x, y, z;
    private float pitch, yaw;

    public LocationObject(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public LocationObject add(double x, double y, double z) {
        setX(getX() + x);
        setY(getY() + y);
        setZ(getZ() + z);
        return this;
    }

    public LocationObject subtract(double x, double y, double z) {
        setX(getX() - x);
        setY(getY() - y);
        setZ(getZ() - z);
        return this;
    }

    public Location toLocation(World w) {
        return new Location(w, getX(), getY(), getZ());
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", getX());
        map.put("y", getY());
        map.put("z", getZ());
        return map;
    }

    @Override
    public String toString() {
        String contents = "";
        for (Map.Entry<String, Object> objectEntry : serialize().entrySet()) {
            contents += " " + objectEntry.getKey() + "=" + String.valueOf(((Number) objectEntry.getValue()).intValue());
        }
        return "[" + contents + " ]";
    }


    @Override
    public LocationObject clone() {
        return new LocationObject(getX(), getY(), getZ());
    }
}
