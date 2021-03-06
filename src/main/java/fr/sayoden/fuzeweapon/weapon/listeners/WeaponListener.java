package fr.sayoden.fuzeweapon.weapon.listeners;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.utils.ItemUtils;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

        String name = (String) ItemUtils.getTag(itemInHand, "name", String.class);

        if (name != null) {
            Optional<AWeapon> weapon = FuzeWeapon.getPlugin().getWeaponService().findWeaponWithId(name);
            if (weapon.isPresent()) {
                event.setCancelled(true);
                weapon.get().shoot(player);
            }
        }
    }

}
