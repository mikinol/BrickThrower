package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        PlayerInventory inventory = e.getPlayer().getInventory();
        ItemStack item = (Main.getServerVersion().equals("1.8"))
                ? inventory.getItem(inventory.getHeldItemSlot())
                : inventory.getItemInOffHand();

        if(item == null) {
            // Sometimes this is air
            return;
        }

        if(Main.getCon().getBoolean("allow-throw-without-nbt-tag")) {
            if (Main.getCon().getStringList("items").contains(item.getType().name().toUpperCase())) {
                e.setCancelled(true);
            }
        }else {
            if (Main.getNBTData(item, "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }
    }
}
