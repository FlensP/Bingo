package fr.flens.bingo.objects;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.objects.gamemodes.Gamemode;
import fr.flens.bingo.objects.gamemodes.Rush;
import fr.flens.bingo.utils.Banlist;
import fr.flens.bingo.utils.ItemCreator;
import fr.flens.bingo.utils.WorldUtils;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Game {

    private int team_size = 1;
    private final ArrayList<BPlayer> players = new ArrayList<>(); //List of BPlayer object for every player who are playing
    private final ArrayList<BPlayer> bPlayers = new ArrayList<>(); //List of BPlayer object for every player connected
    private final ArrayList<BPlayer> disconnectedPlayers = new ArrayList<>();

    private ArrayList<Material> allItemsAllowed = (ArrayList<Material>) Banlist.getItem_allowed().clone();
    private final ArrayList<Material> itemList = new ArrayList<>();
    public BPlayerBoard board;
    private GameState state;

    private Gamemode gamemode;

    private int time;

    public void init() {
        try {
            WorldUtils.PasteBarrier(Objects.requireNonNull(Bukkit.getWorld("lobby")), new Location(Bukkit.getWorld("lobby"), 0, 151, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.state = GameState.Open;
        generateItems();
        gamemode = new Rush();
    }

    public void preStart() {
        this.state = GameState.Launching;

        for (BPlayer bPlayer : bPlayers) {
            if (bPlayer.getTeam() == null) {
                broadcast("§cAu moins un joueur n'est pas dans une Team, il est donc impossible de lancer la partie !");
                System.out.println(bPlayer.getPlayer().getDisplayName());
                return;
            }
        }

        start();
    }

    public void start() {
        gamemode.init((ArrayList<Team>) Bingo.getInstance().getTeamManager().getTeams());

        for (BPlayer bplayer : bPlayers) {
            Player player = bplayer.getPlayer();
            if (bplayer.getTeam() != null) {
                player.setDisplayName(bplayer.getTeam().getPrefix() + player.getName());
            }
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
            broadcast("Téléportation de §7" + player.getName());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(40);
            player.setExp(0);
            player.setLevel(0);
            player.setPlayerListName(player.getDisplayName());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20, 10000, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10 * 20, -1, false, false));
            players.add(bplayer);
            bplayer.setState(PlayerState.Playing);

            board = Netherboard.instance().createBoard(player, "§6§lBingo");
            board.setAll(
                    "§7§m+-------------------+",
                    "§c>> Équipe: " + bplayer.getTeam().getName(),
                    "§c>> Joueurs: §f" + Bukkit.getOnlinePlayers().size(),
                    "§c>> Mode de jeu: §f" + gamemode.toString(),
                    "§c>> Temps: ",
                    "§f§7§m+-------------------+"
            );
        }
        Bukkit.getWorld("world").setTime(0);
        Bukkit.getWorld("world").setStorm(false);
        Bukkit.getWorld("world").setThundering(false);
        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getWorld("world").setDifficulty(Difficulty.NORMAL);
        time = 0;
        Bukkit.getServer().getPluginManager().registerEvents(gamemode, Bingo.getInstance());
        Bukkit.getScheduler().runTaskTimer(Bingo.getInstance(), () -> {
            time++;
            for (Player player : Bukkit.getOnlinePlayers()) {
                board = Netherboard.instance().getBoard(player);
                board.set("§c>> Joueurs: §f" + players.size(), 1);
                board.set("§c>> Temps : §f" + getTime(time), 3);
            }
        }, 10 * 20, 20);

        this.state = GameState.Playing;
    }

    public void win(Team team) {
        broadcast("La team " + team.getName() + " a gagné la partie");
        this.state = GameState.Restarting;
    }

    public void restart() {
        this.state = GameState.Open;
    }

    public void makePlayerJoin(Player player) {
        player.teleport(new Location(Bukkit.getWorld("lobby"), 0, 151, 0));
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.setHealth(20);
        player.setFoodLevel(40);
        broadcast(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + player.getDisplayName());
        ItemStack ItemTeams = new ItemCreator(Material.BLACK_BANNER, 0).setName("§cSélection des Teams").getItem();
        player.getInventory().setItem(0, ItemTeams);
        player.updateInventory();

        if (player.hasPermission("bingo.config")) {
            ItemStack ItemConfig = new ItemCreator(Material.COMPASS, 0).setName("§5Config").getItem();
            player.getInventory().setItem(4, ItemConfig);
            player.updateInventory();
        }
    }

    public void setSpectatorMode(Player player, String s) {
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.GREEN + s);
        BPlayer bPlayer = new BPlayer(player);
        bPlayer.setState(PlayerState.Spectator);
        bPlayers.add(bPlayer);
    }

    public void generateItems() {
        allItemsAllowed = (ArrayList<Material>) Banlist.getItem_allowed().clone();
        itemList.clear();
        for (int i = 0; i < 25; i++) {
            addItem(itemList, i);
        }
    }

    private void addItem(ArrayList<Material> itemList, int i) {
        Material mat = allItemsAllowed.get(Bingo.getR().nextInt(allItemsAllowed.size()));
        allItemsAllowed.remove(mat);
        if (itemList.size() != i) {
            itemList.set(i, mat);
        } else {
            itemList.add(mat);
        }
    }

    public String getTime(int s) {

        int h = (s - s % 3600) / 3600;
        s = s % 3600;
        int m = (s - s % 60) / 60;
        s = s % 60;

        String msg = "";
        if (h > 0) {
            if (h < 10) {
                msg += "0" + h + ":";
            } else {
                msg += h + ":";
            }
        }
        if (m < 10) {
            msg += "0" + m + ":";
        } else {
            msg += m + ":";
        }
        if (s < 10) {
            msg += "0" + s;
        } else {
            msg += s;
        }

        return msg;
    }


    public void setTeam_size(int team_size) {
        this.team_size = team_size;
    }

    public int getTeam_size() {
        return team_size;
    }

    public BPlayer getBPlayer(Player player) {
        for (BPlayer bPlayer : bPlayers) {
            if (bPlayer.getPlayer().getDisplayName().equals(player.getDisplayName())) return bPlayer;
        }
        return null;
    }

    public void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public ArrayList<BPlayer> getPlayers() {
        return players;
    }

    public ArrayList<BPlayer> getBPlayers() {
        return bPlayers;
    }

    public ArrayList<BPlayer> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    public GameState getState() {
        return state;
    }

    public ArrayList<Material> getItemList() {
        return itemList;
    }


    public void makePlayerLeave(Player player) {
        broadcast(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + player.getDisplayName());
        bPlayers.remove(getBPlayer(player));
        if (getBPlayer(player).getTeam() == null) return;
        getBPlayer(player).getTeam().removeBPlayers(getBPlayer(player));
        getBPlayer(player).setTeam(null, true);
    }

    public void makePlayerLeave(BPlayer bPlayer){
        broadcast(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + bPlayer.getPlayer().getDisplayName());
        bPlayers.remove(bPlayer);
        if (bPlayer.getTeam() == null) return;
        bPlayer.getTeam().removeBPlayers(bPlayer);
        bPlayer.setTeam(null, true);
    }

    public ArrayList<BPlayer> getbPlayers() {
        return bPlayers;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public int getTime() {
        return time;
    }
}
