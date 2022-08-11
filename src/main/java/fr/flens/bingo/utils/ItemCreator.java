package fr.flens.bingo.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

public class ItemCreator {

    private ItemStack item;
    private int slot;
    final private static Map<DyeColor, Material> bannerMap = new HashMap<>();
    final private static Map<Material, DyeColor> colorMap = new HashMap<>();

    static {

        bannerMap.put(DyeColor.WHITE, Material.WHITE_BANNER);
        bannerMap.put(DyeColor.ORANGE, Material.ORANGE_BANNER);
        bannerMap.put(DyeColor.MAGENTA, Material.MAGENTA_BANNER);
        bannerMap.put(DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_BANNER);
        bannerMap.put(DyeColor.YELLOW, Material.YELLOW_BANNER);
        bannerMap.put(DyeColor.LIME, Material.LIME_BANNER);
        bannerMap.put(DyeColor.PINK, Material.PINK_BANNER);
        bannerMap.put(DyeColor.GRAY, Material.GRAY_BANNER);
        bannerMap.put(DyeColor.LIGHT_GRAY, Material.LIGHT_GRAY_BANNER);
        bannerMap.put(DyeColor.CYAN, Material.CYAN_BANNER);
        bannerMap.put(DyeColor.PURPLE, Material.PURPLE_BANNER);
        bannerMap.put(DyeColor.BLUE, Material.BLUE_BANNER);
        bannerMap.put(DyeColor.BROWN, Material.BROWN_BANNER);
        bannerMap.put(DyeColor.GREEN, Material.GREEN_BANNER);
        bannerMap.put(DyeColor.RED, Material.RED_BANNER);
        bannerMap.put(DyeColor.BLACK, Material.BLACK_BANNER);


        colorMap.put(Material.WHITE_BANNER, DyeColor.WHITE);
        colorMap.put(Material.ORANGE_BANNER, DyeColor.ORANGE);
        colorMap.put(Material.MAGENTA_BANNER, DyeColor.MAGENTA);
        colorMap.put(Material.LIGHT_BLUE_BANNER, DyeColor.LIGHT_BLUE);
        colorMap.put(Material.YELLOW_BANNER, DyeColor.YELLOW);
        colorMap.put(Material.LIME_BANNER, DyeColor.LIME);
        colorMap.put(Material.PINK_BANNER, DyeColor.PINK);
        colorMap.put(Material.GRAY_BANNER, DyeColor.GRAY);
        colorMap.put(Material.LIGHT_GRAY_BANNER, DyeColor.LIGHT_GRAY);
        colorMap.put(Material.CYAN_BANNER, DyeColor.CYAN);
        colorMap.put(Material.PURPLE_BANNER, DyeColor.PURPLE);
        colorMap.put(Material.BLUE_BANNER, DyeColor.BLUE);
        colorMap.put(Material.BROWN_BANNER, DyeColor.BROWN);
        colorMap.put(Material.GREEN_BANNER, DyeColor.GREEN);
        colorMap.put(Material.RED_BANNER, DyeColor.RED);
        colorMap.put(Material.BLACK_BANNER, DyeColor.BLACK);

    }

    private ArrayList<Pattern> patterns;

    public ItemCreator(final Material material, final int byteID) {
        item = new ItemStack(material, 1, (byte) byteID);
    }

    public ItemCreator(final ItemStack item) {
        setMaterial(item.getType());
        setAmount(item.getAmount());
        setDurability(item.getDurability());
        setName(item.getItemMeta().getDisplayName());
        setEnchantments(item.getItemMeta().getEnchants());
        setLores(item.getItemMeta().getLore());
    }

    public ItemCreator(final ItemCreator itemcreator) {
        this.item = itemcreator.getItem();
        this.slot = itemcreator.slot;
        this.patterns = itemcreator.patterns;
    }

    public ItemStack getItem() {
        return item;
    }

    public Material getMaterial() {
        return item.getType();
    }

    public ItemCreator setMaterial(final Material material) {
        if (item == null) {
            item = new ItemStack(material);
        } else {
            item.setType(material);
        }
        return this;
    }

    public ItemCreator setUnbreakable(final Boolean unbreakable) {
        final ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public Integer getAmount() {
        return item.getAmount();
    }

    public ItemCreator setAmount(final Integer amount) {
        item.setAmount(amount);
        return this;
    }

    public Short getDurability() {
        return item.getDurability();
    }

    public ItemCreator setDurability(final short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemCreator setDurability(final int durability) {
        final short shortdurability = ((short) durability);
        item.setDurability(shortdurability);
        return this;
    }

    public int getDurabilityInteger() {
        return item.getDurability();
    }

    public ItemMeta getMeta() {
        return item.getItemMeta();
    }

    public ItemCreator setMeta(final ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return item.getItemMeta().getDisplayName();
    }

    public ItemCreator setName(final String name) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ArrayList<String> getLores() {
        return (ArrayList<String>) item.getItemMeta().getLore();
    }

    public ItemCreator setLores(final List<String> list) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(list);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearLores() {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(new ArrayList<>());
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator insertLores(final String lore, final Integer position) {
        final ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null) {
            lores = new ArrayList<>();
        }
        lores.add(position, lore);
        meta.setLore(lores);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addLore(final String lore) {
        final ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null) {
            lores = new ArrayList<>();
        }
        if (lore != null) {
            lores.add(lore);
        } else {
            lores.add(" ");
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeLore(final String lore) {
        final ItemMeta meta = item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null) {
            if (lores.contains(lore)) {
                lores.remove(lore);
                meta.setLore(lores);
                item.setItemMeta(meta);
            }
        }
        return this;
    }

    public String[] getTableauLores() {
        if (item.getItemMeta().getLore() != null) {
            return item.getItemMeta().getLore().toArray(new String[0]);
        } else {
            return null;
        }
    }

    public ItemCreator setTableauLores(final String[] lores) {
        final ArrayList<String> tableaulores = new ArrayList<>(Arrays.asList(lores));
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(tableaulores);
        item.setItemMeta(meta);
        return this;
    }

    public HashMap<Enchantment, Integer> getEnchantments() {
        return new HashMap<>(item.getItemMeta().getEnchants());
    }

    public ItemCreator setEnchantments(final Map<Enchantment, Integer> map) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
        }
        for (final Map.Entry<Enchantment, Integer> e : map.entrySet()) {
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearEnchantments() {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addEnchantment(final Enchantment enchantment, final Integer lvl) {
        final ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, lvl, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeEnchantment(final Enchantment enchantment) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            if (meta.getEnchants().containsKey(enchantment)) {
                meta.removeEnchant(enchantment);
                item.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemCreator setTableauEnchantments(final Enchantment[] enchantments, final Integer[] enchantmentslvl) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
        }
        for (int i = 0; i < enchantments.length && i < enchantmentslvl.length; i++) {
            meta.addEnchant(enchantments[i], enchantmentslvl[i], true);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ArrayList<ItemFlag> getItemFlags() {
        final ArrayList<ItemFlag> itemflags = new ArrayList<>();
        if (item.getItemMeta().getItemFlags() != null) {
            itemflags.addAll(item.getItemMeta().getItemFlags());
        }
        return itemflags;
    }

    public ItemCreator setItemFlags(final ArrayList<ItemFlag> itemflags) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<>(meta.getItemFlags());
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
        }
        for (final ItemFlag itemflag : itemflags) {
            meta.addItemFlags(itemflag);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearItemFlags() {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<>(meta.getItemFlags());
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addItemFlags(final ItemFlag itemflag) {
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(itemflag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeItemFlags(final ItemFlag itemflag) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags() != null) {
            if (meta.getItemFlags().contains(itemflag)) {
                meta.removeItemFlags(itemflag);
                item.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemCreator createBanner(DyeColor color) {
        item = new ItemStack(bannerMap.get(color));
        return this;
    }

    public ItemCreator setColor(final Color basecolor) {
        if (item.getType().equals(Material.LEATHER_BOOTS) || item.getType().equals(Material.LEATHER_CHESTPLATE) || item.getType().equals(Material.LEATHER_HELMET) || item.getType().equals(Material.LEATHER_LEGGINGS)) {
            final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(basecolor);
            item.setItemMeta(meta);
        } else if (bannerMap.containsValue(item.getType())) {
            item.setType(bannerMap.get(DyeColor.getByColor(basecolor)));
        }
        return this;
    }

    public ArrayList<Pattern> getPatterns() {
        if (bannerMap.containsValue(item.getType())) {
            return (ArrayList<Pattern>) ((BannerMeta) item.getItemMeta()).getPatterns();
        }
        return null;
    }

    public ItemCreator setPatterns(final ArrayList<Pattern> petterns) {
        if (bannerMap.containsValue(item.getType())) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.setPatterns(petterns);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearPatterns() {
        if (bannerMap.containsValue(item.getType())) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.setPatterns(new ArrayList<>());
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addPattern(final Pattern pattern) {
        if (bannerMap.containsValue(item.getType())) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.addPattern(pattern);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setTableauPatterns(final Pattern[] patterns) {
        if (bannerMap.containsValue(item.getType())) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            if (meta.getPatterns() != null) {
                meta.setPatterns(new ArrayList<>());
            }
            for (final Pattern pattern : patterns) {
                meta.addPattern(pattern);
            }
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addallItemsflags() {
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addBannerPreset(final int ID, final DyeColor patterncolor) {
        switch (ID) {
            case 1:
                addBannerPreset(BannerPreset.barre, patterncolor);
                break;
            case 2:
                addBannerPreset(BannerPreset.precedent, patterncolor);
                break;
            case 3:
                addBannerPreset(BannerPreset.suivant, patterncolor);
                break;
            case 4:
                addBannerPreset(BannerPreset.coeur, patterncolor);
                break;
            case 5:
                addBannerPreset(BannerPreset.cercleEtoile, patterncolor);
                break;
            case 6:
                addBannerPreset(BannerPreset.croix, patterncolor);
                break;
            case 7:
                addBannerPreset(BannerPreset.yinYang, patterncolor);
                break;
            case 8:
                addBannerPreset(BannerPreset.losange, patterncolor);
                break;
            case 9:
                addBannerPreset(BannerPreset.moin, patterncolor);
                break;
            case 10:
                addBannerPreset(BannerPreset.plus, patterncolor);
                break;
            default:
                break;
        }
        return this;
    }

    public ItemCreator addBannerPreset(final BannerPreset type, final DyeColor patterncolor) {
        if (type == null)
            return this;
        if (bannerMap.containsValue(item.getType())) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            final DyeColor basecolor = colorMap.get(item.getType());
            switch (type) {
                // rien, barre, precedent, suivant, Coeur, cercleEtoile, croix,
                // yinYang, Losange, Moin, Plus;
                case barre -> addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_DOWNRIGHT), true);
                case precedent -> {
                    // precedent
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_BOTTOM_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), true);
                }
                case suivant -> {
                    // suivant
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_BOTTOM_LEFT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_LEFT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_LEFT), true);
                }
                case coeur -> {
                    // Coeur
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.TRIANGLE_TOP), true);
                }
                case cercleEtoile -> {
                    // cercleEtoile
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.FLOWER), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.CIRCLE_MIDDLE), true);
                }
                case croix -> {
                    // croix
                    addasyncronePattern(new Pattern(patterncolor, PatternType.CROSS), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.CURLY_BORDER), true);
                }
                case yinYang -> {
                    // yinYang
                    addasyncronePattern(new Pattern(patterncolor, PatternType.SQUARE_BOTTOM_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.DIAGONAL_LEFT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_LEFT), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.TRIANGLE_TOP), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.TRIANGLE_BOTTOM), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_LEFT), true);
                }
                case losange ->
                    // Losange
                        addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), true);
                case moin -> {
                    // Moin
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.BORDER), true);
                }
                case plus -> {
                    // Plus
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRAIGHT_CROSS), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_TOP), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_BOTTOM), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.BORDER), true);
                }
                default -> {
                }
            }
        }
        return this;
    }

    private void addasyncronePattern(final Pattern pattern, final Boolean calcul) {
        if (calcul) {
            patterns.add(pattern);
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            for (final Pattern currentpattern : patterns) {
                meta.addPattern(currentpattern);
            }
            patterns.clear();
            item.setItemMeta(meta);

        } else {
            if (patterns == null) {
                patterns = new ArrayList<>();
            }
            patterns.add(pattern);
        }
    }

    public enum BannerPreset {
        barre, precedent, suivant, coeur, cercleEtoile, croix, yinYang, losange, moin, plus
    }

}
