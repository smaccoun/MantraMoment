package com.me.stevenmaccoun.mantramoment;

import java.util.Random;

/**
 * Created by stevenmaccoun on 3/15/16.
 */
public class MantraGenerator {

    private static String[] DefaultMantras = new String[]{
            "I AM",
            "I AM EMBODIED",
            "I EXIST",
            "PALM AWARENESS",
            "GRAVITY",
            "ORGANISM",
            "CELL STATE",
            "WATER BODY",
            "HEART",
            "BREATH",
            "THAT IS MYSELF",
            "I AM THAT",
            "SKULL/TEETH",
            "FEET",
            "NOW",
            "Outside Sounds",
            "Inner Body",
            "Sight Perceptions",
            "Animal",
            "Monkey",
            "Intersubjective Space",
            "Sovereign",
            "Headless",
            "Witness"
    };

    public static String getRandomDefaultMantra(){
        int idx = new Random().nextInt(DefaultMantras.length);
        return DefaultMantras[idx];
    }
}
