package fr.sayoden.fuzeweapon.weapon.weapons;

import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;

public class LaserWeapon extends AWeapon {

    private final BlockFace[] minimalValues;

    public LaserWeapon() {
        super("LaserWeapon",
                "Laser",
                "description",
                new FuzeItemStack(new ItemStack(Material.DIAMOND))
                        .setName("Laser"));

        setFiringRate(getFiringRate() / 10);
        this.minimalValues = new BlockFace[]{BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    }

    @Override
    public void shoot(Player shooter) {
        super.shoot(shooter);
        Location start = shooter.getLocation();
        Location end = start.clone().add(shooter.getLocation().getDirection().multiply(100));
        this.laserbeam(start, end, 30 * 10, shooter);
    }

    private void laserbeam(Location start, Location end, int repeatCount, Player p) {
        double dist = Math.abs(end.distance(start));
        for (int i = -1; i < repeatCount; ++i) {

            double delta = (double) i / 10.0D / dist;
            double x = (1.0D - delta) * start.getX() + delta * (end.getX() + 0.5D);
            double y = (1.0D - delta) * start.getY() + delta * (end.getY() + 0.5D);
            double z = (1.0D - delta) * start.getZ() + delta * (end.getZ() + 0.5D);
            Location location = new Location(start.getWorld(), x, y, z);

            if (i % 10 == 0) {
                p.getWorld().spawnParticle(Particle.FLAME, location, 0);
            }

            Block block;
            if (i > 5) {
                block = start.getWorld().getBlockAt(location);
                if (block.getType().isSolid()) {
                    Vector oldV = location.clone().toVector().subtract(start.clone().toVector());
                    Vector lotV = this.getLot(location);
                    double val = 2.0D * oldV.clone().dot(lotV.clone());
                    Vector newV = oldV.clone().subtract(lotV.clone().multiply(val));
                    this.laserbeam(location, location.clone().add(newV), repeatCount - i, p);
                    break;
                }
            }
        }
    }

    private Vector getLot(Location location) {
        BlockFace smallestBF = BlockFace.UP;
        double smallestDist = 1000.0D;
        BlockFace[] minimalVals = this.minimalValues;

        for (BlockFace bf : minimalVals) {
            Block b = location.getBlock().getRelative(bf);
            Location tempMidBlock = this.getMidLocation(b.getLocation());
            if (location.distanceSquared(tempMidBlock) < smallestDist) {
                smallestDist = location.distanceSquared(tempMidBlock);
                smallestBF = bf;
            }
        }

        if (smallestBF == BlockFace.UP) {
            return new Vector(0, 1, 0);
        } else if (smallestBF == BlockFace.DOWN) {
            return new Vector(0, 1, 0);
        } else if (smallestBF == BlockFace.NORTH) {
            return new Vector(0, 0, 1);
        } else if (smallestBF == BlockFace.EAST) {
            return new Vector(1, 0, 0);
        } else if (smallestBF == BlockFace.SOUTH) {
            return new Vector(0, 0, 1);
        } else {
            return new Vector(1, 0, 0);
        }
    }

    private Location getMidLocation(Location l) {
        return new Location(l.getWorld(), (double) l.getBlockX() + 0.5D, (double) l.getBlockY() + 0.5D, (double) l.getBlockZ() + 0.5D);
    }
}
