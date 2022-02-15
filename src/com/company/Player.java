package com.company;

import java.util.ArrayList;

public class Player {

    private String name;
    private int time;
    private int id;
    private ArrayList<Compare> compare;

    public Player(String name, int zeit, int id, ArrayList<Compare> compare) {
        this.name = name;
        this.time = zeit;
        this.compare = compare;
        this.id = id;
    }


    public void addCompare(Player o, int value) {
        Compare listed = isAlreadyComparedTo(o);
        Compare cur = new Compare(this, o, value);
        if (listed != null) {
            compare.remove(listed);
            compare.add(cur);
            return;
        }
        compare.add(cur);
    }

    public Compare isAlreadyComparedTo(Player o) {
        for (Compare c : compare) {
            if (c.getA().equals(this) && c.getB().equals(o)) {
                return c;
            }
        }
        return null;
    }

    public void candidatesSorted() {
        compare.sort((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getZeit() {
        return time;
    }

    public Player setZeit(int zeit) {
        this.time = zeit;
        return this;
    }

    public ArrayList<Compare> getCompare() {
        return compare;
    }

    public Player setCompare(ArrayList<Compare> compare) {
        this.compare = compare;
        return this;
    }

    public int getID() {
        return this.id;
    }

    public int liking(Player o) {
        for (Compare c : compare) {
            Player b = c.getB();
            if (b.getID() == o.getID()) {
                return c.getValue();
            }
        }
        return 0;
    }

    public double groupWeight(Group g) {
        double weight = 1;
        for (Player p : g.getPlayersInGroup()) {
            weight *= (10.0 / this.liking(p));
        }
        if (g.getPlayersInGroup().isEmpty()) {
            return 1;
        }
        return Math.pow(weight, 1d / g.getPlayersInGroup().size());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
