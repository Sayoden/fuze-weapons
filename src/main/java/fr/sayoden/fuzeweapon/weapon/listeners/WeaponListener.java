package fr.sayoden.fuzeweapon.weapon.listeners;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.utils.ItemUtils;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import fr.sayoden.fuzeweapon.weapon.tasks.ShootTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class WeaponListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (itemInHand == null || itemInHand.getItemMeta() == null) {
            return;
        }

        Optional<AWeapon> weapon = FuzeWeapon.getPlugin().getWeaponService().findWeaponWithId((String) ItemUtils.getTag(itemInHand, "name", String.class));

        if (weapon.isPresent()) {
            new ShootTask(player, weapon.get(), player.getLocation(), player.getEyeLocation());
            Bukkit.broadcastMessage((String) ItemUtils.getTag(itemInHand, "name", String.class));
            Bukkit.broadcastMessage("Bullets " + ItemUtils.getTag(itemInHand, "bullets", Integer.class));
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FuzeWeapon.getPlugin().getWeaponService().giveWeaponToPlayer(event.getPlayer(), "MachineGunWeapon");
    }

}
