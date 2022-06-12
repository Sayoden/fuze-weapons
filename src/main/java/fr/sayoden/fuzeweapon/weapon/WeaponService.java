package fr.sayoden.fuzeweapon.weapon;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.weapon.weapons.LaserWeapon;
import fr.sayoden.fuzeweapon.weapon.weapons.MachineGunWeapon;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

public class WeaponService {

    @Getter
    private static ArrayList<AWeapon> weapons;

    /**
     * Init weapons
     */
    public WeaponService() {
        FuzeWeapon plugin = FuzeWeapon.getPlugin();
        weapons = new ArrayList<>();
        weapons.add(new MachineGunWeapon());
        weapons.add(new LaserWeapon());
    }

    /**
     *
     * @param weaponId - the name of the weapon
     * @return - null if the weapon doesn't exist or the target weapon
     */
    public Optional<AWeapon> findWeaponWithId(String weaponId) {
        return weapons.stream()
                .filter(aWeapon -> aWeapon.getClass().getSimpleName().equalsIgnoreCase(weaponId))
                .findFirst();
    }

    public void giveWeaponToPlayer(Player player, String weaponId) {
        findWeaponWithId(weaponId).ifPresent(aWeapon -> aWeapon.giveWeaponToPlayer(player));
    }
}