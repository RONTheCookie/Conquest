package net.warvale.conquest.utils.misc;

import java.util.Random;

public class NumberUtils {

    public static Integer random(Integer max, Integer min) {
        return new Random().nextInt((max - min) + 1) + min;
    }

}
