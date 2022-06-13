package fr.sayoden.fuzeweapon.weapon.weapons;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.utils.FuzeItemStack;
import fr.sayoden.fuzeweapon.utils.FuzeWeaponConstants;
import fr.sayoden.fuzeweapon.weapon.AWeapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.Color;

public class PoseidonWeapon extends AWeapon {

    private static final int TSUNAMI_SIZE = 40;

    private final int zoneEffectRange;

    public PoseidonWeapon() {
        super("PoseidonWeapon",
                "Sceptre de Poséidon",
                "description",
                new FuzeItemStack(new ItemStack(Material.STICK))
                        .setName("Sceptre de Poséidon"));

        setFiringRate(getFiringRate() * 5);
        this.zoneEffectRange = 10;
    }

    @Override
    public void shoot(Player shooter) {
        super.shoot(shooter);
        shooter.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 105, 3));
        shooter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 3));
        shooter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 3));

        playEntityEffects(shooter);

        World world = Bukkit.getWorld("world");
        world.setTime(20000);
        world.setWeatherDuration(20 * 10);

        playTsunami(shooter);

    }

    private void playEntityEffects(Player player) {
        player.getNearbyEntities(zoneEffectRange * 2, zoneEffectRange * 2, zoneEffectRange * 2).forEach(entity -> {
            new BukkitRunnable() {
                private double x = 0;

                @Override
                public void run() {
                    x += 0.1;
                    Location entityLocation = entity.getLocation();
                    entityLocation.setY(entityLocation.getY() + 0.1);
                    entity.teleport(entityLocation);

                    if (x >= 8) {
                        entity.remove();
                        new ParticleBuilder(ParticleEffect.CLOUD)
                                .setLocation(entityLocation)
                                .setColor(Color.RED)
                                .setAmount(250)
                                .setOffset(0.2f, 0.2f, 0.2f)
                                .display();
                        cancel();
                    }
                }
            }.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 2L);
        });
    }

    private void playTsunami(Player player) {
        Bukkit.broadcastMessage("§4§lMouahahahahahaha !!! Tu vas comprendre la terreur");
        player.sendMessage("§8§oCours le plus vite possible pour échapper au Tsunami...");
        final World world = Bukkit.getWorld("world");
        Location tsunamiStartLocation = player.getLocation();
        tsunamiStartLocation.setX(tsunamiStartLocation.getX() + (TSUNAMI_SIZE * 2));
        fillChunkWater(tsunamiStartLocation);
        new BukkitRunnable() {
            private double y = tsunamiStartLocation.getY() + (TSUNAMI_SIZE * 2);
            private double x = tsunamiStartLocation.getX();

            @Override
            public void run() {
                for (double z = tsunamiStartLocation.getZ() - TSUNAMI_SIZE; z <= tsunamiStartLocation.getZ() + (TSUNAMI_SIZE * 3); z++) {
                    Location location = new Location(world, x, y, z);
                    Block blockAt = world.getBlockAt(location);
                    if (blockAt.getType().equals(Material.AIR)) {
                        int randomNumber = FuzeWeaponConstants.RANDOM.nextInt(100 + 1) + 1;
                        if (randomNumber <= 2) {
                            Bukkit.getWorld("world").strikeLightningEffect(blockAt.getLocation());
                        } else {
                            blockAt.setType(Material.WATER);
                        }
                    }
                }
                y = y - 2;
                x--;

                if (x <= tsunamiStartLocation.getX() - TSUNAMI_SIZE) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 3));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 3));
                    submersionEffect(tsunamiStartLocation);
                    cancel();
                }
            }
        }.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 18L);
    }

    private void submersionEffect(Location tsunamiLocation) {
        System.out.println("Destroy map effect");
        final double sizeEffect = 120;
        final double initialYLocation = tsunamiLocation.getY() - 20;

        new BukkitRunnable() {
            private double x = tsunamiLocation.getX() - TSUNAMI_SIZE;

            @Override
            public void run() {
                for (double z = tsunamiLocation.getZ() - TSUNAMI_SIZE; z <= tsunamiLocation.getZ() + (TSUNAMI_SIZE * 3); z++) {
                    Location location = new Location(Bukkit.getWorld("world"), x, initialYLocation, z);
                    while (true) {
                        if (location.getBlock().getType().equals(Material.AIR)) {
                            location.getBlock().setType(Material.WATER);
                            break;
                        } else {
                            location.add(0, 1, 0);
                        }
                    }
                }

                x--;
                if (x <= (tsunamiLocation.getX() - TSUNAMI_SIZE) - sizeEffect) {
                    cancel();
                }
            }
        }.runTaskTimer(FuzeWeapon.getPlugin(), 0L, 5L);
    }

    private void fillChunkWater(Location tsunamiLocation) {
        final int chunkWaterSize = 20;
        tsunamiLocation.setZ(tsunamiLocation.getZ() - TSUNAMI_SIZE);

        for (double z = tsunamiLocation.getZ() - TSUNAMI_SIZE; z <= tsunamiLocation.getZ() + (TSUNAMI_SIZE * 3); z++) {
            for (double x = tsunamiLocation.getX(); x <= tsunamiLocation.getX() + chunkWaterSize; x++) {
                Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, tsunamiLocation.getY() + (TSUNAMI_SIZE * 2), z)).setType(Material.WATER);
            }
        }
    }
}
