package fr.sayoden.fuzeweapon.weapon.tasks;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ShootTask extends BukkitRunnable {

    private static final int MAX_ITERATION = 100;

    private final Player player;

    private final AWeapon weapon;

    private final Location bulletLocation;

    private final Location eyesLocation;

    private int limit;

    public ShootTask(Player player, AWeapon weapon, Location bulletLocation, Location eyesLocation) {
        this.player = player;
        this.weapon = weapon;
        this.bulletLocation = bulletLocation;
        this.eyesLocation = eyesLocation;
        this.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 40L);
    }

    @Override
    public void run() {
        limit++;

        if (limit == MAX_ITERATION) {
            this.cancel();
        }

        Bukkit.broadcastMessage("Limit: " + limit);
    }
}
