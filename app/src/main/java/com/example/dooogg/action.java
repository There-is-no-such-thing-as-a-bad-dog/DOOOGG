package com.example.dooogg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class action {
    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("time")
    @Expose
    private int time;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
