package com.company;

import java.util.ArrayList;

public class Group {

    public ArrayList<Player> playersInGroup;

    public Group() {
        playersInGroup = new ArrayList<>();
    }

    public Player getBestPlayerForGroup(ArrayList<Player> allRemainingPlayers) {
        double likingWeight[] = new double[allRemainingPlayers.size()];
        for (int i = 0; i < allRemainingPlayers.size(); i++) {
            /*
             Calculates the weight of likings between each member of the group.
             For example:
                If two people are in a group the combined "liking" value would be:
                "Math.pow(PlayerOneOfGroup.liking(PlayerXOfPossiblePlayers) * PlayerTwoOfGroup.liking(PlayerXOfPossiblePlayers), 0.5)"

                If n people are in a group the combined "liking" value would be:
                "Math.pow(PlayerOnOfGroup.liking(PlayerXOfPossiblePlayer) * PlayerTwoOfGroup.liking(PlayerXOfPossiblePlayer) * ... * PlayerNOfGroup.liking(PlayerXOfPossiblePlayer), 1 / n);"
             */
            likingWeight[i] = getWeightForPlayer(allRemainingPlayers.get(i));
        }
        return getHighestLiking(likingWeight, allRemainingPlayers);
    }

    private Player getHighestLiking(double[] likingWeight, ArrayList<Player> allRemainingPlayers) {
        double top = -1;
        int topIndex = -1;
        for (int i = 0; i < likingWeight.length; i++) {
            if (likingWeight[i] > top) {
                top = likingWeight[i];
                topIndex = i;
            }
        }
        if (top == -1) {
            return null;
        }
        return allRemainingPlayers.get(topIndex);
    }

    public double getWeightForPlayer(Player o) {
        double out = 1;
        if (playersInGroup.isEmpty()) {
            return 1;
        }
        /*
            According to the "intensity" the result will be more and more randomized.
        */
        double intensity = 2;
        for (Player p : playersInGroup) {
            if (p.liking(o) < 4) {
                out *= 0;
            }
            out *= p.liking(o) * Math.pow(Math.random(), intensity);
        }
        return Math.pow(out, 1d / playersInGroup.size());
    }


    public ArrayList<Player> getPlayersInGroup() {
        return playersInGroup;
    }

    public Group setPlayersInGroup(ArrayList<Player> playersInGroup) {
        this.playersInGroup = playersInGroup;
        return this;
    }

}
