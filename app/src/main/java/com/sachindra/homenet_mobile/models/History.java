package com.sachindra.homenet_mobile.models;

import com.google.gson.annotations.SerializedName;

public class History {
    private String sound;
    private String time;

    @SerializedName("_id")
    private Id _id;

    public History() {
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }
}
