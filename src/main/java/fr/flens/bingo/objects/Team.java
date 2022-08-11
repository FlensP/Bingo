package fr.flens.bingo.objects;

import fr.flens.bingo.utils.ItemCreator;

import java.util.ArrayList;

public class Team {

    private final String prefix;
    private final String name;
    private final ArrayList<BPlayer> bPlayers;
    private final ItemCreator item;

    public Team(String prefix, String name, ItemCreator item) {
        this.prefix = prefix;
        this.name = name;
        this.bPlayers = new ArrayList<>();
        this.item = item;
    }

    public String playerToString() {

        if (bPlayers.size() > 0){
            StringBuilder txt = new StringBuilder("§d");

            for (BPlayer bPlayer : bPlayers){
                txt.append(bPlayer.getPlayer().getDisplayName()).append(", ");
            }

            return txt.substring(0, txt.length() - 2);
        } else {
            return "§7Aucun...";
        }
    }


    public ItemCreator getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ArrayList<BPlayer> getbPlayers() {
        return bPlayers;
    }

    public void addBPlayer(BPlayer bPlayer) { if (!bPlayers.contains(bPlayer)) bPlayers.add(bPlayer); }

    public void removeBPlayers(BPlayer bPlayer) { bPlayers.remove(bPlayer); }

}
