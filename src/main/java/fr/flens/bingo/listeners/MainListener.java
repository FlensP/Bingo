package fr.flens.bingo.listeners;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.guis.TeamPickerGUI;
import fr.flens.bingo.objects.BPlayer;
import fr.flens.bingo.objects.Game;
import fr.flens.bingo.objects.GameState;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MainListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(null);
        Game game = Bingo.getInstance().getGame();

        if (game.getState().equals(GameState.Open)) {
            game.getBPlayers().add(new BPlayer(player));
            game.makePlayerJoin(player);
        } else {

            boolean remove = false;
            for (BPlayer bPlayer : game.getDisconnectedPlayers()) {

                if (bPlayer.getPlayer().getName().equals(player.getName())) {
                    bPlayer.setPlayer(player);
                    game.getDisconnectedPlayers().remove(bPlayer);
                    bPlayer.getPlayer().setDisplayName(bPlayer.getTeam().getPrefix() + bPlayer.getPlayer().getName());
                    bPlayer.getPlayer().setPlayerListName(player.getDisplayName());
                    remove = true;
                    break;
                }
            }

            if (remove) {
                game.broadcast(player.getName() + "vient de se reconnecter.");
            } else {
                game.setSpectatorMode(player, "La partie a déjà commencé. Vous êtes spectateur");
            }
        }

        if (game.getState().equals(GameState.Playing)) {
            BPlayerBoard board = Netherboard.instance().createBoard(player.getPlayer(), "§6§lBingo");
            String teamName;
            if (game.getBPlayer(player) == null) {
                teamName = "§7Spectateur";
            } else {
                teamName = game.getBPlayer(player).getTeam().getName();
            }
            board.setAll(
                    "§7§m+-------------------+",
                    "§c>> Équipe: " + teamName,
                    "§c>> Joueurs: §f" + Bukkit.getOnlinePlayers().size(),
                    "§c>> Mode de jeu: §f" + game.getGamemode().toString(),
                    "§c>> Temps: " + game.getTime(game.getTime()),
                    "§f§7§m+-------------------+"
            );
        }


        player.updateInventory();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        event.setQuitMessage(null);
        Player player = event.getPlayer();
        Game game = Bingo.getInstance().getGame();
        if (game.getBPlayer(player) == null) return;
        BPlayer bPlayer = game.getBPlayer(player);

        if (!game.getState().equals(GameState.Playing)) {
            game.makePlayerLeave(bPlayer);
        } else {
            if (game.getPlayers().contains(game.getBPlayer(player))) {
                game.broadcast(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + player.getDisplayName() + ChatColor.YELLOW + "a quitté la partie.");
                game.getDisconnectedPlayers().add(bPlayer);
            } else {
                game.makePlayerLeave(bPlayer);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack it = event.getItem();

        if (it == null) return;

        if ((it.getType() == Material.BLACK_BANNER || it.getType() == Material.GREEN_BANNER) && (it.hasItemMeta() && it.getItemMeta().getDisplayName().equals("§cSélection des Teams")) && Bingo.getInstance().getGame().getState().equals(GameState.Open)) {
            TeamPickerGUI.TEAMPICKER.open(player);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Bingo.getInstance().getGame().getState().equals(GameState.Playing)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Bingo.getInstance().getGame().getState().equals(GameState.Playing)) return;
        event.setCancelled(true);
    }


    @EventHandler
    public void onDamagebyEntity(EntityDamageByEntityEvent event) {
        if (!Bingo.getInstance().getGame().getState().equals(GameState.Playing)) {
            event.setCancelled(true);
        } else {
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
                event.setCancelled(true);
            } else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
                if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (Bingo.getInstance().getGame().getState().equals(GameState.Playing)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (Bingo.getInstance().getGame().getState().equals(GameState.Playing)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (Bingo.getInstance().getGame().getState().equals(GameState.Playing)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }


}
