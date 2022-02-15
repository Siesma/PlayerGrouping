package com.company;

public class Compare {

    private Player a, b;
    private int value;

    public Compare(Player a, Player b, int value) {
        this.a = a;
        this.b = b;
        this.value = value;
    }

    public Player getA() {
        return a;
    }

    public Player getB() {
        return b;
    }

    public int getValue() {
        return value;
    }

    public Compare setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return a.getName() + " likes " + b.getName() + " " + value + "/10";
    }
}
