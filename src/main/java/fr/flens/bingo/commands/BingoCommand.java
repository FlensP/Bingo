package fr.flens.bingo.commands;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.guis.BingoGUI;
import fr.flens.bingo.objects.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BingoCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (Bingo.getInstance().getGame().getState().equals(GameState.Open)) {
                sender.sendMessage("Vous ne pouvez pas ouvrir le bingo avant le début de la partie");
                return false;
            }

            BingoGUI.BINGO.open((Player) sender);
        }
        return false;
    }
}
