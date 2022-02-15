package com.company;

import java.io.IOException;
import java.util.ArrayList;

public class Handler {

    ArrayList<Player> playerList = new ArrayList<>();


    private static final int GROUP_SIZE = 3;

    public Handler() {
    }

    public void start() {
        try {
            run();
        } catch (Exception e) {
            System.out.println("Catched an \"" + e.getClass() + "\"! ");
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void run() throws Exception {
        String suffix = "/src/DataBase/";
        playerList = loadPlayerList(suffix + "PlayersAsList.txt");
        fillPlayerPriorities(playerList, suffix + "Matching Daten.csv", false);
        sortPlayerList(playerList);


        System.out.println(getPlayerByID(playerList, 1).liking(getPlayerByID(playerList, 3)));

        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 0; i < (int) Math.floor((float) playerList.size() / GROUP_SIZE) + 1; i++) {
            groups.add(new Group());
        }
        for (Group g : groups) {
            for (int i = 0; i < GROUP_SIZE; i++) {
                if (playerList.size() == 0) {
                    break;
                }
                ArrayList<Player> waiting = new ArrayList<>();
                Player best = g.getBestPlayerForGroup(playerList);
                while (best.groupWeight(g) < 0.75) {
                    waiting.add(best);
                    playerList.remove(best);
                    best = g.getBestPlayerForGroup(playerList);
                    if (best == null) {
                        best = waiting.get((int) (Math.random() * waiting.size()));
                        break;
                    }
                }
                playerList.addAll(waiting);
                waiting.clear();
                g.getPlayersInGroup().add(best);
                playerList.remove(best);
            }
        }

        int index = 0;
        for (Group g : groups) {
            System.out.println("Group " + ('a' + index++));
            for (Player p : g.playersInGroup) {
                System.out.println("\t" + p.getName());
            }
        }

        printGroup(groups.get(0));
    }


    public void printGroup(Group g) {
        System.out.println("Testing");
        g.playersInGroup.forEach(e -> System.out.println("\t" + e.toString()));

        for (Player p : g.playersInGroup) {
            for (Player o : g.playersInGroup) {
                if (p.getID() == o.getID()) {
                    continue;
                }
                System.out.println(p.toString() + " is liking " + o.toString() + "  " + p.liking(o));
            }
        }


    }

    public void sortPlayerList(ArrayList<Player> playerArrayList) {
        playerArrayList.forEach(Player::candidatesSorted);
    }


    public void fillPlayerPriorities(ArrayList<Player> players, String pathToFile, boolean printIDInformations) throws IOException {
        String prefix = System.getProperty("user.dir");
        String fileContent = (new FileInterpreter()).readFromInputStream(prefix + pathToFile);
        ArrayList<String> unusedIDs = new ArrayList<>();
        for (String s : fileContent.split("\n")) {
            if (!s.matches("[0-9]+,[0-9]+,[0-9]+")) {
                continue;
            }
            String[] splitted = s.split(",");
            int myID = Integer.valueOf(splitted[0]);
            int theirID = Integer.valueOf(splitted[1]);
            int liking = Integer.valueOf(splitted[2]);
            Player playerMyID = getPlayerByID(players, myID);
            Player playerTheirID = getPlayerByID(players, theirID);
            if (playerMyID == null) {
                unusedIDs.add("As sender: " + myID);
                continue;
            }
            if (playerTheirID == null) {
                unusedIDs.add("As sender: " + myID);
                continue;
            }
            playerMyID.addCompare(playerTheirID, liking);

        }
        if (printIDInformations) {
            System.out.println("The following IDs were called but are not used as any Player according to the Database.");
            for (String s : unusedIDs) {
                System.out.println("\t" + s);
            }
        }
    }

    private Player getPlayerByID(ArrayList<Player> players, int id) {
        for (Player p : players) {
            if (p.getID() == id) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Player> loadPlayerList(String pathToFile) throws IOException {
        String prefix = System.getProperty("user.dir");
        ArrayList<Player> out = new ArrayList<>();
        String fileContent = (new FileInterpreter()).readFromInputStream(prefix + pathToFile);


        for (String s : fileContent.split("\n")) {
            String[] splitted = s.split(",");
            int id = Integer.parseInt(splitted[0]);
            String name = splitted[1] + " " + splitted[2];
            int zeit = 0;
            Player cur = new Player(name, zeit, id, new ArrayList<>());
            out.add(cur);
        }
        return out;
    }
}
