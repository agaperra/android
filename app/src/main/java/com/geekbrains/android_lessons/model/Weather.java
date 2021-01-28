package com.geekbrains.android_lessons.model;

public class Weather {
    private String main;
    private String description;
    private String icon;
    private int id;


    public int getId(){return  id;}
    public void setId(int id){ this.id=id;}
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
    public String getIcon(){return icon;}
    public void setIcon(String icon){this.icon=icon;}
}
