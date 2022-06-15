package fr.sayoden.fuzeweapon.weapon.weapons;

import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MachineGunWeapon extends AWeapon {

    public MachineGunWeapon() {
        super("MachineGunWeapon",
                5,
                "Mitraillette",
                "description",
                new FuzeItemStack(new ItemStack(Material.CARROT_ON_A_STICK))
                        .setName("Mitraillette"));

        setFiringRate(getFiringRate() / 10);
    }

    @Override
    public void shoot(Player shooter) {
        super.shoot(shooter);
        Block block = shooter.getTargetBlock(null, 50);
        block.setType(Material.AIR);
        shootParticle(shooter, Particle.FLAME, 2.5);
    }
}
