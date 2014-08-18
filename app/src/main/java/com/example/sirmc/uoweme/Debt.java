package com.example.sirmc.uoweme;

import com.orm.SugarRecord;

public class Debt extends SugarRecord<Debt> {
    String name;
    double amount;

    public Debt(){
    }

    public Debt(String name, double amount){
        this.name = name;
        this.amount = amount;
    }
}