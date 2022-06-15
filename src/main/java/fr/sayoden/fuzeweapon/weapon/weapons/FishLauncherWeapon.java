package fr.sayoden.fuzeweapon.weapon.weapons;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class FishLauncherWeapon extends AWeapon {

    public FishLauncherWeapon() {
        super("FishLauncherWeapon",
                15,
                "Lanceur de poisson",
                "description",
                new FuzeItemStack(new ItemStack(Material.CARROT_ON_A_STICK))
                        .setName("Lanceur de poisson"));

        setFiringRate(getFiringRate() * 5);
    }

    @Override
    public void shoot(Player shooter) {
        super.shoot(shooter);
        Location location = shooter.getEyeLocation();
        Item item = shooter.getWorld().dropItem(location, new ItemStack(Material.TROPICAL_FISH));

        item.setVelocity(location.getDirection().multiply(1.75D));
        item.setPickupDelay(Integer.MAX_VALUE);

        //Task to check if the fish is on ground
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorld("world");
                if (!world.getBlockAt(item.getLocation().add(0, -1, 0)).getType().equals(Material.AIR)) {
                    makeExplosion(item, world);
                    cancel();
                }
            }
        }.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 10L);
    }

    private void makeExplosion(Item item, World world) {
        Bukkit.broadcastMessage("§4§lBOUGE VITE, CE POISSON EST SUPER DANGEREUX!!!");
        new BukkitRunnable() {
            private int timer = 0;

            @Override
            public void run() {
                timer++;
                Bukkit.broadcastMessage("§b§lMECHANT POISSON: §b" + (timer % 2 == 0 ? "Tac" : "Tic") + " ...");
                Entity pig = world.spawnEntity(item.getLocation(), EntityType.PIG);
                Vector pigVelocity = pig.getVelocity();
                pigVelocity.setY(3.5);
                pig.setVelocity(pigVelocity);

                if (timer == 6) {
                    new ParticleBuilder(ParticleEffect.REDSTONE)
                            .setLocation(item.getLocation())
                            .setColor(Color.RED)
                            .setAmount(50)
                            .setOffset(0.5f, 0.5f, 0.5f)
                            .display();
                    item.remove();
                    Bukkit.broadcastMessage("§b§lJE T'AVAIS PREVENU!!! PLOUFFFFFF!!!! .... HEU BOUMMMMMMM");
                    Bukkit.getWorld("world").createExplosion(item.getLocation(), 20);
                    cancel();
                }
            }
        }.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 20L);
    }
}
