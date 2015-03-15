package com.razbyte.personalassistant;


public class Movement {
    String name;
    int dist;

    public Movement(String name, int dist){
        this.name = name;
        this.dist = dist;
    }

    public int get_dist(){
        return this.dist;
    }

    public String get_name(){
        return this.name;
    }
}
