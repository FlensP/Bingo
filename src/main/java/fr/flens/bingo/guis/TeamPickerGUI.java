package fr.flens.bingo.guis;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.objects.Team;
import fr.flens.bingo.utils.InventoryUtils;
import fr.flens.bingo.utils.ItemCreator;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TeamPickerGUI implements InventoryProvider {

    private Bingo plugin;

    public TeamPickerGUI() {
        this.plugin = Bingo.getInstance();
    }

    public static final SmartInventory TEAMPICKER = SmartInventory.builder()
            .id("teamPickerGUI")
            .provider(new TeamPickerGUI())
            .size(6, 9)
            .title("Sélections des équipes")
            .manager(Bingo.getInstance().getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        ItemStack blackGlass = new ItemCreator(Material.BLACK_STAINED_GLASS_PANE, 0).setName(" ").getItem();
        inventoryContents.fill(ClickableItem.empty(new ItemStack(blackGlass)));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

        int index = 0;
        for (Team team : plugin.getTeamManager().getTeams()) {
            ItemStack item = new ItemStack(team.getItem()
                    .setName(team.getName())
                    .setLores(Arrays.asList("§7", "§7Joueurs: " + team.playerToString() + "§7 (" + team.getbPlayers().size() + "/" + plugin.getGame().getTeam_size() + ")")).getItem());

            ClickableItem clickableItem = ClickableItem.of(item, e -> {
                if (team.getbPlayers().size() < plugin.getGame().getTeam_size()) {
                    plugin.getGame().getBPlayer((Player) e.getWhoClicked()).setTeam(team, false);
                }
            });

            inventoryContents.set(InventoryUtils.slot(index + 10).get(0), InventoryUtils.slot(index + 10).get(1), clickableItem);
            if (index == 6 || index == 15 || index == 24) {
                index += 3;
            } else index++;
        }

    }
}