package fr.sayoden.fuzeweapon.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FuzeItemStack extends ItemStack {

    public FuzeItemStack(ItemStack item) {
        super(item);
    }

    public FuzeItemStack(Material material, int amount, byte data) {
        super(material, amount, (short)data);
    }

    public FuzeItemStack(Material material, int amount) {
        super(material, amount);
    }

    public FuzeItemStack(Material material) {
        super(material);
    }

    public FuzeItemStack setName(String displayName) {
        if (displayName != null && !displayName.equals("")) {
            ItemMeta meta = this.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            this.setItemMeta(meta);
        }
        return this;
    }

    public FuzeItemStack setLore(String lore) {
        return this.setLore(lore, true);
    }

    public FuzeItemStack setLore(String lore, boolean removeAttributes) {
        if (lore != null && !lore.equals("")) {
            if (lore.contains("cutlines:")) {
                return this.setLore(lore, "");
            } else {
                ItemMeta meta = this.getItemMeta();
                if (removeAttributes) {
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }

                lore = ChatColor.translateAlternateColorCodes('&', lore);
                String[] loreString = lore.split("\n");
                meta.setLore(new ArrayList(Arrays.asList(loreString)));
                this.setItemMeta(meta);
                return this;
            }
        } else {
            return this;
        }
    }

    public FuzeItemStack setLeatherColor(Color color) {
        LeatherArmorMeta lch = (LeatherArmorMeta)this.getItemMeta();
        lch.setColor(color);
        this.setItemMeta(lch);
        return this;
    }

    public FuzeItemStack setLore(String... lines) {
        return this.setLore(true, lines);
    }

    public FuzeItemStack setLore(boolean removeAttributes, String... lines) {
        if (lines != null && lines.length != 0) {
            StringBuilder finalLore = new StringBuilder();
            String[] var4 = lines;
            int var5 = lines.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String line = var4[var6];
                if (line != null) {
                    finalLore.append(line.startsWith("cutlines:") ? createLines(line.replace("cutlines:", ""), 160) : line);
                }

                finalLore.append("\n");
            }

            return this.setLore(finalLore.toString(), removeAttributes);
        } else {
            return this;
        }
    }

    public FuzeItemStack setFuzeAmount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public FuzeItemStack setUnbreakable(boolean unbreakable) {
        ItemMeta meta = this.getItemMeta();
        meta.setUnbreakable(unbreakable);
        this.setItemMeta(meta);
        return this;
    }

    public FuzeItemStack setData(byte data) {
        MaterialData materialData = this.getData();
        materialData.setData(data);
        this.setData(materialData);
        return this;
    }

    public FuzeItemStack setGlow(boolean enabled) {
        if (enabled) {
            this.setGlow();
        }

        return this;
    }

    public FuzeItemStack setGlow() {
        this.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 0);
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(meta);
        return this;
    }

    public FuzeItemStack addFuzeEnchantment(Enchantment enchantment, int level) {
        super.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    private void addLineToLore(String... lines) {
        ItemMeta meta = this.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList();
        }

        int i = -1;
        String[] var5 = lines;
        int var6 = lines.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String line = var5[var7];
            ++i;
            if (i != 0 || line != null || this.hasAlreadyLore()) {
                ((List)lore).add(line == null ? "" : line);
            }
        }

        meta.setLore((List)lore);
        this.setItemMeta(meta);
    }

    public String createLines(String lore, int size) {
        boolean addExclamation = false;
        if (lore.endsWith(" !")) {
            lore = lore.substring(0, lore.length() - 2);
            addExclamation = true;
        }

        if (lore.startsWith("&")) {
            lore = lore.replaceFirst("&", "§");
        }

        int i = 0;
        String str = "";
        String[] splitlore = lore.split(" ");
        String color = "";
        if (lore.substring(0, 2).startsWith("§")) {
            size -= 10;
            color = lore.substring(0, 2);
        }

        int a = 0;
        String[] var8 = splitlore;
        int var9 = splitlore.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            String string = var8[var10];
            char[] var12 = string.toCharArray();
            int var13 = var12.length;

            for(int var14 = 0; var14 < var13; ++var14) {
                char var10000 = var12[var14];
                i += 5;
            }

            str = str + string + " ";
            if (i >= size && a < splitlore.length - 1) {
                str = str + "\n" + color;
                i = 0;
            } else {
                i += 4;
            }

            ++a;
        }

        return str + (addExclamation ? (str.endsWith(" ") ? "" : " ") + "!" : "");
    }

    private boolean hasAlreadyLore() {
        ItemMeta meta = this.getItemMeta();
        if (meta == null) {
            return false;
        } else {
            List<String> lore = meta.getLore();
            return lore != null && lore.size() > 0;
        }
    }
}