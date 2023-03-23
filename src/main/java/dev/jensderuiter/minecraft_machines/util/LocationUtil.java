package dev.jensderuiter.minecraft_machines.util;

public class LocationUtil {

    public static boolean isSame(int locX, int locY, int locZ, int otherLocX, int otherLocY, int otherLocZ) {
        if (locX == otherLocX && locY == otherLocY && locZ == otherLocZ) {
            return true;
        }
        return false;
    }

}
