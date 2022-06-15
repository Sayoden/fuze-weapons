package fr.sayoden.fuzeweapon.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class LocationUtils {

    public static Location getRandomLocation(Location location, int radius) {
        final int randX = FuzeWeaponConstants.RANDOM.nextInt(2 * radius) - radius;
        final int randZ = FuzeWeaponConstants.RANDOM.nextInt(2 * radius) - radius;
        Location randomLocation = new Location(FuzeWeaponConstants.MAIN_WORLD, location.getX() + randX, 0, location.getZ() + randZ);
        for (int i = 1; i < 255; i++) {
            randomLocation.setY(i);
            if (checkIfIsCorrectPosition(randomLocation)) {
                return randomLocation;
            }
        }
        return null;
    }

    public static boolean checkIfIsCorrectPosition(Location location) {
        return location.getBlock().getType().equals(Material.AIR)
                && (location.getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)
                || location.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR));
    }

}
