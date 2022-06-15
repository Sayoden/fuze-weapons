package fr.sayoden.fuzeweapon.entity;

import fr.sayoden.fuzeweapon.utils.FuzeWeaponConstants;
import fr.sayoden.fuzeweapon.utils.LocationUtils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;

import java.util.List;

public class FriendlyEntity {

    private final World world;

    private final Mob entity;

    private final Player player;

    private Bat travelEntity;

    public FriendlyEntity(Location location, Player player) {
        this.player = player;
        this.world = location.getWorld();
        this.entity = (Mob) world.spawnEntity(location, EntityType.ZOMBIE);
        entity.setInvulnerable(true);
        this.travelEntity = (Bat) world.spawnEntity(location.clone().add(0, 2, 0),
                EntityType.BAT);
        travelEntity.setAI(false);
        travelEntity.setInvisible(true);
        travelEntity.setInvulnerable(false);
        travelEntity.setSilent(true);

        entity.setTarget(travelEntity);
    }

    public void updatePositions() {
        travelEntity.setInvulnerable(false);
        travelEntity.remove();
        this.travelEntity = (Bat) world.spawnEntity(LocationUtils.getRandomLocation(player.getLocation(), 2).add(0, 2d, 0),
                EntityType.BAT);
        travelEntity.setAI(false);
        travelEntity.setInvisible(true);
        travelEntity.setInvulnerable(false);
        travelEntity.setSilent(true);

        List<Entity> entities = player.getNearbyEntities(3, 3, 3);
        if (!(entities.get(0).getType().equals(EntityType.ZOMBIE) || entities.get(0).getType().equals(EntityType.BAT) || entities.get(0).getType().equals(EntityType.PLAYER))) {
            entity.setTarget((LivingEntity) entities.get(0));
        } else {
            entity.setTarget(travelEntity);
        }

    }

    public void depop() {
        travelEntity.setInvulnerable(false);
        travelEntity.remove();
        entity.setInvulnerable(false);
        entity.remove();
    }
}