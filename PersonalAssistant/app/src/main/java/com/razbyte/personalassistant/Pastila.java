package com.razbyte.personalassistant;

public class Pastila{
    public String name;

    public String toString(){
        String data = "";
        data = data.concat(this.name + "$");
        return data;
    }

    public void set_name(String name){
        this.name = name;
    }



}
