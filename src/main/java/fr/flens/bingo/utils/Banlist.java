package fr.flens.bingo.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Banlist {

    private static final ArrayList<Material> ban = new ArrayList<>();
    private static final ArrayList<Material> all_items = new ArrayList<>(List.of(Material.values()));
    private static final ArrayList<Material> item_allowed = new ArrayList<>();


    public static void init() {
        initBan();
        item_allowed.clear();

        for (int i = 0; i <= 1100; i++) { //after 1100, it's items you cannot get (like upper part of a door)
            if (i < 40 || i > 57) { //Ore -> skill touch
                if (i < 276 || i > 282) { //infested stones
                    if (i < 451 || i > 467){ //shulkers
                        if (i < 873 || i > 939){ //spawn eggs
                            if (i < 1015 || i > 1028){ //disc
                                if (!ban.contains(all_items.get(i))){
                                    item_allowed.add(all_items.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void initBan() {
        //DOESN'T EXIST IN HAND
        ban.add(Material.AIR);

        //CANNOT OBTAIN
        ban.add(Material.BEDROCK);
        ban.add(Material.SPAWNER);
        ban.add(Material.END_PORTAL_FRAME);
        ban.add(Material.DRAGON_EGG);
        ban.add(Material.COMMAND_BLOCK);
        ban.add(Material.BARRIER);
        ban.add(Material.LIGHT);
        ban.add(Material.REPEATING_COMMAND_BLOCK);
        ban.add(Material.CHAIN_COMMAND_BLOCK);
        ban.add(Material.STRUCTURE_VOID);
        ban.add(Material.STRUCTURE_BLOCK);
        ban.add(Material.JIGSAW);
        ban.add(Material.POTION);
        ban.add(Material.EXPERIENCE_BOTTLE);
        ban.add(Material.COMMAND_BLOCK_MINECART);
        ban.add(Material.KNOWLEDGE_BOOK);
        ban.add(Material.DEBUG_STICK);
        //TOO DIFFICULT
        ban.add(Material.GRASS_BLOCK);
        ban.add(Material.WITHER_ROSE);
        ban.add(Material.BEACON);
        ban.add(Material.TURTLE_EGG);
        ban.add(Material.ELYTRA);
        ban.add(Material.DRAGON_BREATH);
        ban.add(Material.TOTEM_OF_UNDYING);
        ban.add(Material.NETHER_STAR);

    }

    public static ArrayList<Material> getItem_allowed() {
        return item_allowed;
    }
}
