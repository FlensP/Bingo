package fr.flens.bingo;

import fr.flens.bingo.commands.BingoCommand;
import fr.flens.bingo.commands.StartCommand;
import fr.flens.bingo.listeners.MainListener;
import fr.flens.bingo.managers.TeamManager;
import fr.flens.bingo.objects.Game;
import fr.flens.bingo.utils.Banlist;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Bingo extends JavaPlugin {

    private static Bingo instance;
    private final InventoryManager invManager = new InventoryManager(this);
    private Game game;
    private TeamManager teamManager;
    private static final Random r = new Random();

    @Override
    public void onEnable() {
        instance = this;
        Banlist.init();
        game = new Game();
        teamManager = new TeamManager();

        getServer().getPluginManager().registerEvents(new MainListener(), this);

        //Création du jeu
        WorldCreator wc = new WorldCreator("world_the_end");
        wc.environment(World.Environment.THE_END);
        wc.createWorld();
        wc = new WorldCreator("world_nether");
        wc.environment(World.Environment.NETHER);
        wc.createWorld();
        wc = new WorldCreator("world");
        wc.environment(World.Environment.NORMAL);
        wc.createWorld();
        wc = new WorldCreator("lobby");
        wc.environment(World.Environment.NORMAL);
        wc.createWorld();

        World lobby = Bukkit.getWorld("lobby");
        lobby.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        lobby.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        World world = Bukkit.getWorld("world");
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        world.setGameRule(GameRule.NATURAL_REGENERATION, true);

        getCommand("bingo").setExecutor(new BingoCommand());
        getCommand("start").setExecutor(new StartCommand());

        game.init();
        invManager.init();
    }



    public static Bingo getInstance() {
        return instance;
    }

    public Game getGame() {
        return game;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public InventoryManager getInvManager() {
        return invManager;
    }

    public static Random getR() {
        return r;
    }
}
