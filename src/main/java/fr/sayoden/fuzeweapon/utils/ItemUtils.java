package fr.sayoden.fuzeweapon.utils;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static ItemStack setTag(ItemStack stack, String key, Object value) {
        NBTTagCompound tag = getTag(stack);

        net.minecraft.server.v1_16_R3.ItemStack item = CraftItemStack.asNMSCopy(stack);

        if (value instanceof String) {
            tag.setString(key, (String) value);
        } else if (value instanceof Long) {
            tag.setLong(key, (Long) value);
        } else if (value instanceof Integer) {
            tag.setInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            tag.setBoolean(key, (Boolean) value);
        }

        item.setTag(tag);

        return CraftItemStack.asBukkitCopy(item);
    }


    public static Object getTag(ItemStack stack, String key, Class<?> type) {
        NBTTagCompound tag = getTag(stack);

        Object val = null;

        if (!tag.hasKey(key)) {
            return val;
        }

        if (type == String.class) {
            val = tag.getString(key);
        } else if (type == Long.class) {
            val = tag.getLong(key);
        } else if (type == Integer.class) {
            val = tag.getInt(key);
        } else if (type == Boolean.class) {
            val = tag.getBoolean(key);
        }

        return val;
    }

    private static NBTTagCompound getTag(ItemStack stack) {
        net.minecraft.server.v1_16_R3.ItemStack item = CraftItemStack.asNMSCopy(stack);

        NBTTagCompound tag = null;

        if (item.hasTag()) {
            tag = item.getTag();
        } else {
            tag = new NBTTagCompound();
        }

        return tag;
    }

}
