package fr.sayoden.fuzeweapon.weapon.weapons;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.entity.FriendlyEntity;
import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.utils.FuzeWeaponConstants;
import fr.sayoden.fuzeweapon.utils.LocationUtils;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class InvokerWeapon extends AWeapon {
    
    private static final int MOBS_AMOUNT = 10;

    public InvokerWeapon() {
        super("InvokerWeapon",
                25,
                "Invocateur de Zombie",
                "description",
                new FuzeItemStack(new ItemStack(Material.CARROT_ON_A_STICK))
                        .setName("Invocateur de Zombie"));

        setFiringRate(getFiringRate() / 10);
    }

    @Override
    public void shoot(Player shooter) {
        super.shoot(shooter);

        for (int i = 0; i < MOBS_AMOUNT; i++) {
            Location spawnEntityLocation = LocationUtils.getRandomLocation(shooter.getLocation(), 5);

            if (spawnEntityLocation == null) {
                spawnEntityLocation = shooter.getLocation();
            }

            FriendlyEntity friendlyEntity = new FriendlyEntity(spawnEntityLocation, shooter);
            new BukkitRunnable() {
                private int timer = 0;
                @Override
                public void run() {
                    timer++;
                    if (timer == 30 * 15) {
                        friendlyEntity.depop();
                        cancel();
                    }
                    friendlyEntity.updatePositions();
                }
            }.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 5L);
        }
    }
}
