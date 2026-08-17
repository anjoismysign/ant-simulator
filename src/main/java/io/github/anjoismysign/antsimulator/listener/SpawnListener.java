package io.github.anjoismysign.antsimulator.listener;

import io.github.anjoismysign.antsimulator.AntSimulator;
import io.github.anjoismysign.antsimulator.director.manager.AntConfigurationManager;
import io.github.anjoismysign.bloblib.translatable.TranslatableArea;
import io.github.anjoismysign.bloblib.translatable.TranslatablePositionable;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.Nullable;

public class SpawnListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onLaunch(ProjectileLaunchEvent event){
        ProjectileSource projectileSource = event.getEntity().getShooter();
        if (!(projectileSource instanceof Player playerShooter)){
            return;
        }
        event.setCancelled(Boolean.TRUE.equals(isInSpawn(playerShooter)));
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event){
        Entity damaged = event.getEntity();
        if (damaged.getType() != EntityType.PLAYER){
            return;
        }
        Player damagedPlayer = (Player) damaged;
        event.setCancelled(Boolean.TRUE.equals(isInSpawn(damagedPlayer)));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRespawn(PlayerRespawnEvent event) {
        String spawnTranslatablePositionableIdentifier = AntConfigurationManager.getConfiguration().getSpawn();
        @Nullable TranslatablePositionable spawnTranslatablePositionable = TranslatablePositionable.by(spawnTranslatablePositionableIdentifier);
        if (spawnTranslatablePositionable == null){
            AntSimulator.getInstance().getLogger().info("'"+spawnTranslatablePositionableIdentifier+"' is not a valid TranslatablePositionable! Players might spawn over roof!");
            return;
        }
        Location location = spawnTranslatablePositionable.get().toLocation();
        event.setRespawnLocation(location);
    }

    @Nullable
    public Boolean isInSpawn(Entity entity){
        String spawnTranslatableAreaIdentifier = AntConfigurationManager.getConfiguration().getSpawn();
        @Nullable TranslatableArea spawnTranslatableArea = TranslatableArea.by(spawnTranslatableAreaIdentifier);
        if (spawnTranslatableArea == null){
            AntSimulator.getInstance().getLogger().info("'"+spawnTranslatableAreaIdentifier+"' is not a valid TranslatableArea! PvP is enabled in spawn!");
            return null;
        }
        return spawnTranslatableArea.get().isInside(entity);
    }

}
