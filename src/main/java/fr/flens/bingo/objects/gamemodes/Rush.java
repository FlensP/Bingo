package fr.flens.bingo.objects.gamemodes;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.objects.BPlayer;
import fr.flens.bingo.objects.Game;
import fr.flens.bingo.objects.GameState;
import fr.flens.bingo.objects.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Rush extends Gamemode {

    private final HashMap<Team, ArrayList<Boolean>> itemsGot = new HashMap<>();
    private Game game;
    public static ArrayList<int[]> wins = new ArrayList<>();

    public void init(ArrayList<Team> teams) {
        for (Team team : teams) {
            ArrayList<Boolean> items = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                items.add(false);
            }
            itemsGot.put(team, items);
        }
        this.game = Bingo.getInstance().getGame();


        wins.add(new int[]{0, 1, 2, 3, 4});
        wins.add(new int[]{5, 6, 7, 8, 9});
        wins.add(new int[]{10, 11, 12, 13, 14});
        wins.add(new int[]{15, 16, 17, 18, 19});
        wins.add(new int[]{20, 21, 22, 23, 24});
        wins.add(new int[]{0, 5, 10, 15, 20});
        wins.add(new int[]{1, 6, 11, 16, 21});
        wins.add(new int[]{2, 7, 12, 17, 22});
        wins.add(new int[]{3, 8, 13, 18, 23});
        wins.add(new int[]{4, 9, 14, 19, 24});
        wins.add(new int[]{0, 6, 12, 18, 24});
        wins.add(new int[]{4, 8, 12, 16, 20});

    }

    public ArrayList<Boolean> getItemsGot(Team team) {
        return itemsGot.get(team);
    }

    @EventHandler
    public void onPickup(InventoryPickupItemEvent event) {
        if (!game.getState().equals(GameState.Playing)) return;
        Material mat = event.getItem().getItemStack().getType();
        if (!game.getItemList().contains(mat)) return;
        if (!(event.getInventory() instanceof PlayerInventory)) return;
        Player player = (Player) event.getInventory().getHolder();
        BPlayer bPlayer = game.getBPlayer(player);
        if (!game.getPlayers().contains(bPlayer)) return;

        itemsGot.get(bPlayer.getTeam()).set(game.getItemList().indexOf(mat), true);

        checkWin(bPlayer.getTeam());
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!game.getState().equals(GameState.Playing)) return;
        Material mat = Objects.requireNonNull(event.getInventory().getResult()).getType();
        if (!game.getItemList().contains(mat)) return;
        Player player = (Player) event.getInventory().getHolder();
        BPlayer bPlayer = game.getBPlayer(player);
        if (!game.getPlayers().contains(bPlayer)) return;

        itemsGot.get(bPlayer.getTeam()).set(game.getItemList().indexOf(mat), true);

        checkWin(bPlayer.getTeam());
    }

    @EventHandler
    public void onBucketChange(PlayerBucketFillEvent event) {
        if (!game.getState().equals(GameState.Playing)) return;
        Material mat = event.getBucket();
        if (!game.getItemList().contains(mat)) return;
        Player player = event.getPlayer();
        BPlayer bPlayer = game.getBPlayer(player);
        if (!game.getPlayers().contains(bPlayer)) return;

        itemsGot.get(bPlayer.getTeam()).set(game.getItemList().indexOf(mat), true);

        checkWin(bPlayer.getTeam());
    }

    public void checkWin(Team team) {
        ArrayList<Boolean> list = itemsGot.get(team);
        for (int[] a : wins) {
            boolean have_row = false;
            for (int i = 0; i < 5; i++) {
                if (list.get(a[i])) {
                    have_row = true;
                } else {
                    have_row = false;
                    break;
                }
            }
            if (have_row){
                game.win(team);
                break;
            }
        }
    }

    @Override
    public String toString(){
        return "rush";
    }


}
