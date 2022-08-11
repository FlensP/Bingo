package fr.flens.bingo.objects.gamemodes;

import fr.flens.bingo.objects.Team;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public abstract class Gamemode implements Listener {

    public abstract void init(ArrayList<Team> teams);
    public abstract ArrayList<Boolean> getItemsGot(Team team);

}
