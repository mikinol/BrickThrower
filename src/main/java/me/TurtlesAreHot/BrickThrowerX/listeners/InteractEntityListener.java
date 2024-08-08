package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class InteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        PlayerInventory pi = e.getPlayer().getInventory();
        if(!(Main.getServerVersion().equals("1.8"))) {
            if(pi.getItemInOffHand() == null) {
                // Sometimes this is air
                return;
            }
            if (Main.getNBTData(pi.getItemInOffHand(), "brickthrower_item") != null) {
                e.setCancelled(true);
            }

        }

        if(pi.getItem(pi.getHeldItemSlot()) == null) {
            return;
        }
        if(Main.getNBTData(pi.getItem(pi.getHeldItemSlot()), "brickthrower_item") != null) {
            e.setCancelled(true);
        }
    }
}
