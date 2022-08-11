package fr.flens.bingo.objects;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.utils.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BPlayer {

    private Player player;
    private Team team;

    private PlayerState state;

    public BPlayer(Player player) {
        this.player = player;
        this.team = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team, boolean silent) {


        ItemStack banner = new ItemCreator(Material.GREEN_BANNER, 0).setName("§cSelection des Teams").getItem();

        Bingo plugin = Bingo.getInstance();

        if (plugin.getGame().getState().equals(GameState.Playing) && getTeam() != null) {
            plugin.getTeamManager().updateTeams(getTeam());
        }

        if (team == null) {
            if (this.team != null) this.team.removeBPlayers(this);

            this.team = null;
            if (plugin.getGame().getState().equals(GameState.Open)) {

                getPlayer().getInventory().setItem(0, new ItemCreator(Material.BLACK_BANNER, 0).setName("§cSélection des Teams").getItem());
                player.getInventory().setHelmet(null);

            }
            if (!silent)
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + ChatColor.GRAY + "] " + ChatColor.RED + "Vous avez quitté votre équipe");
        } else {
            Team actualTeam = getTeam();
            if (actualTeam != null) {
                actualTeam.removeBPlayers(this);
            }

            if (plugin.getGame().getState().equals(GameState.Open)) {

                getPlayer().getInventory().setItem(0, banner);
                player.getInventory().setHelmet(team.getItem().getItem());

            }

            this.team = team;
            this.team.addBPlayer(this);

            if (!silent)
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + ChatColor.GRAY + "] " + ChatColor.GREEN + "Vous avez rejoint l'equipe " + team.getName());

        }

        if (plugin.getGame().getState().equals(GameState.Playing) && getTeam() != null)
            plugin.getTeamManager().updateTeams(getTeam());

    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}
