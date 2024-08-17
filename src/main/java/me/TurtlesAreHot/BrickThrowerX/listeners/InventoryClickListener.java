package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {
    /**
     * This event is for stonecutters, cartography tables, and looms.
     */
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        InventoryType invType = e.getInventory().getType();
        if(invType == InventoryType.STONECUTTER ||
        invType == InventoryType.CARTOGRAPHY ||
        invType == InventoryType.LOOM) {
            if(Main.getNBTData(e.getCurrentItem(), "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }
    }
}
