package com.example.aman.pccontrol;

import android.widget.ImageView;

/**
 * Created by Yash Dani on 29-03-2018.
 */
public class FileCard {
    private String name,size,display;

    public FileCard() {
    }

    public FileCard(String name, String size, String type) {
        this.name = name;
        this.size = size;
        this.display = type;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display){
        this.display = display;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String genre) {
        this.size = genre;
    }
}

