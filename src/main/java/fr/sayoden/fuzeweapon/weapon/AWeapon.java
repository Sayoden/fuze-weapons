package fr.sayoden.fuzeweapon.weapon;

import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.utils.FuzeWeaponConstants;
import fr.sayoden.fuzeweapon.utils.ItemUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

@Getter
public abstract class AWeapon {

    private final String id;

    private final int customModelData;

    private final String name;

    private final String description;

    private final FuzeItemStack weaponItem;

    /**
     * Firing rate gun in minecraft tick (20 --> 1s)
     */
    @Setter
    private int firingRate;

    /**
     * Reload time in minecraft tick
     */
    @Setter
    private int reloadTime;

    public AWeapon(String id, int customModelData, String name, String description, FuzeItemStack weaponItem) {
        this.id = id;
        this.customModelData = customModelData;
        this.name = name;
        this.description = description;
        this.weaponItem = weaponItem;
        this.firingRate = FuzeWeaponConstants.FIRING_RATE;
        this.reloadTime = FuzeWeaponConstants.RELOAD_TIME;
    }

    public void giveWeaponToPlayer(Player player) {
        ItemStack item = new ItemStack(weaponItem).clone();
        item = ItemUtils.setTag(item, "bullets", 25);
        item = ItemUtils.setTag(item, "name", id);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(customModelData);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void shoot(Player shooter) {
        shooter.playSound(shooter.getLocation(), this.id.toLowerCase(), 1, 1);
    }

    public void shootParticle(Player player, Particle particle, double velocity) {
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection();
        player.getWorld().spawnParticle(particle, location.getX(), location.getY(), location.getZ(), 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), velocity, null);
    }
}
