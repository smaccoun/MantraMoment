package com.me.stevenmaccoun.mantramoment;

import java.util.List;

/**
 * Created by stevenmaccoun on 4/28/16.
 */
public class Mantra {

    private String desc;
    private List<String> tags;

    private enum COMMON_TAGS{
        BODY,
        GESTALT,
        GROUNDING,
        GROSSOME
    }

    public Mantra(String desc){
        this.desc = desc;
        tags = null;
    }

    public Mantra(String desc, List<String> tags){
        this.desc = desc;
        this.tags = tags;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getTags() {
        return tags;
    }
}
