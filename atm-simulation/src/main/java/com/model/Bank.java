package com.model;


public class Bank {

    private final String type;
    private final int value;
    private int amount;

    public Bank(String type, int value){
        this.type = type;
        this.value = value;
    }

    public String getType(){
        return this.type;
    }

    public int getValue(){
        return this.value;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return  this.amount;
    }

    @Override
    public String toString(){
        return "Bank{" +
                " type=" + getType() +
                " value=" + getValue() +
                " amount=" + getAmount() +
                " }";
    }
}
