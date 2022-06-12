package fr.sayoden.fuzeweapon.weapon.weapons;

import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MachineGunWeapon extends AWeapon {

    public MachineGunWeapon() {
        super("MachineGunWeapon",
                "Mitraillette",
                "description",
                new FuzeItemStack(new ItemStack(Material.DIRT))
                        .setName("Mitraillette"));

        setFiringRate(getFiringRate() / 10);
    }



}
