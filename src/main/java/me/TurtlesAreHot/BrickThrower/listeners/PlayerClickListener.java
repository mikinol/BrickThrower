package me.TurtlesAreHot.BrickThrower.listeners;

import me.TurtlesAreHot.BrickThrower.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.List;

public class PlayerClickListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        if((e.getAction() != Action.RIGHT_CLICK_AIR) && (e.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            // This is the case of an invalid action
            return;
        }

        ItemStack itemHeld = e.getItem();

        if(itemHeld == null) {
            // No item held.
            return;
        }

        if ((Main.getNBTData(itemHeld, "brickthrower_item") == null)) {
            // Item not a brickthrower, we don't care.
            return;
        }

        Player player = e.getPlayer();

        // Create the item that will be thrown
        ItemStack thrownItem = new ItemStack(itemHeld.getType(), 1);
        ItemMeta thrownItemMeta = thrownItem.getItemMeta();

        thrownItemMeta.setDisplayName(Main.getCon().getString("item-name"));
        thrownItem.setItemMeta(thrownItemMeta);

        // Starting throwing
        Item thrown = player.getWorld().dropItem(player.getEyeLocation(), thrownItem);

        Vector throwVector = player.getEyeLocation().getDirection().multiply(
                Main.getCon().getDouble("item-velocity-multiplier"));

        thrown.setVelocity(throwVector);

        int disappearTime = Main.getCon().getInt("item-disappear-time");
        if(disappearTime != 0) {
            // If it is zero, the item can just be picked up again, otherwise make it not able to.
            thrown.setPickupDelay(Short.MAX_VALUE);
        }

        // Remove one from player inventory if they are not in creative mode
        if(player.getGameMode() != GameMode.CREATIVE) {
            e.getItem().setAmount(itemHeld.getAmount() - 1);
        }


        Date dropTime = new Date();

        // Remove Thrown Entity from world
        if(disappearTime != 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), thrown::remove,
                    disappearTime * 20L);
        }

        double itemDamage = Main.getCon().getDouble("item-damage");
        if(itemDamage == 0.0) {
            // No damage to apply, stop here
            return;
        }

        // Apply damage and knockback
        new BukkitRunnable() {
            @Override
            public void run() {
                if(disappearTime != 0) {
                    /**
                     * If this item disappears, it shouldn't try to hit an entity!
                     */
                    Date currentTime = new Date();
                    if((currentTime.getTime() - dropTime.getTime()) / 1000 >= disappearTime) {
                        cancel();
                    }
                }

                // Get all entities nearby
                List<Entity> entityList = thrown.getNearbyEntities(0.5, 1.0,  0.5);
                for(Entity entity : entityList) {
                    if(!(entity instanceof LivingEntity)) {
                        // Don't want to hit any non-living entities.
                        continue;
                    }
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if(livingEntity instanceof Player) {
                        /**
                         * Don't want to hit ourselves!
                         */
                        Player hit = (Player) livingEntity;
                        if(hit.getUniqueId().equals(player.getUniqueId())) {
                            continue;
                        }
                        // In creative or blocking with a shield? We should cancel
                        if(hit.getGameMode() == GameMode.CREATIVE ||
                        hit.isBlocking() ) {

                            // Remove the hit, but don't apply damage or velocity.
                            thrown.remove();
                            throwVector.multiply(0);
                            cancel();
                        }else if(hit.getGameMode() == GameMode.SPECTATOR){
                            // Skip a player who is in the spectators.
                            continue;
                        }
                     }

                    // Apply Damage and Knockback
                    livingEntity.damage(itemDamage, player);
                    livingEntity.setVelocity(throwVector);
                    thrown.remove();
                    cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0, 0);

    }



}
