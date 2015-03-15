package com.razbyte.personalassistant;

import java.util.ArrayList;



public class Pastila_list extends ArrayList<Pastila> {

    public ArrayList<String> toString2() {
        ArrayList<String> data_str = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            String data = "";
            data = data.concat(this.get(i).name);
            data_str.add(data);
        }

        return data_str;
    }

}