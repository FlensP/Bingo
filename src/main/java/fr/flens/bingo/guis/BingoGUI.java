package fr.flens.bingo.guis;

import fr.flens.bingo.Bingo;
import fr.flens.bingo.objects.BPlayer;
import fr.flens.bingo.objects.Team;
import fr.flens.bingo.utils.ItemCreator;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class BingoGUI implements InventoryProvider {

    public static final SmartInventory BINGO = SmartInventory.builder()
            .id("bingoGui")
            .provider(new BingoGUI())
            .size(5, 9)
            .title(ChatColor.DARK_PURPLE + "Bingo")
            .manager(Bingo.getInstance().getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {


        contents.fillColumn(0, ClickableItem.empty(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        contents.fillColumn(1, ClickableItem.empty(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        contents.fillColumn(7, ClickableItem.empty(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        contents.fillColumn(8, ClickableItem.empty(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));


    }

    @Override
    public void update(Player player, InventoryContents contents) {

        ItemStack fItem = new ItemCreator(Material.BEDROCK, 0).addEnchantment(Enchantment.KNOCKBACK, 10).addItemFlags(ItemFlag.HIDE_ENCHANTS).setName(ChatColor.GREEN + "» Item trouvé").getItem();

        Bingo plugin = Bingo.getInstance();


        int[] x = {2, 3, 4, 5, 6};
        int[] y = {0, 1, 2, 3, 4};
        int xi = 0;
        int yi = 0;

        BPlayer bPlayer = plugin.getGame().getBPlayer(player);
        if (bPlayer == null) return;

        int i = 0;

        if (bPlayer.getTeam() == null) {
            for (Material item : plugin.getGame().getItemList()) {

                contents.set(y[yi], x[xi], ClickableItem.empty(new ItemStack(item)));
                xi = (xi + 1) % 5;
                if (xi == 0) {
                    yi = (yi + 1) % 5;
                }
                i++;

            }
        } else {
            Team team = bPlayer.getTeam();

            for (Material item : plugin.getGame().getItemList()) {

                if (plugin.getGame().getGamemode().getItemsGot(team).get(i)) {
                    contents.set(y[yi], x[xi], ClickableItem.empty(fItem));
                } else {
                    contents.set(y[yi], x[xi], ClickableItem.empty(new ItemStack(item)));
                }

                xi = (xi + 1) % 5;
                if (xi == 0) {
                    yi = (yi + 1) % 5;
                }

                i++;

            }
        }


    }
}