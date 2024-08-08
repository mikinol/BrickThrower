package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class UtilContentsListener implements Listener {
    // This class contains Anvil and Brewing Event Listeners.
    // The code is the same, so I am practicing DRY as much as possible.
    // Smithing needs its own class because it was added in 1.16

    public boolean eventUsesBrick(ItemStack[] contents) {
        for(int i = 0; i < contents.length; i++) {
            if(contents[i] == null) {
                continue;
            }
            if(Main.getNBTData(contents[i], "brickthrower_item") != null) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent e) {
        if(Main.getServerVersion().equals("1.8")) {
            return;
        }
        if(eventUsesBrick(e.getInventory().getContents())) {
            e.setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onBrew(BrewEvent e) {
        if(eventUsesBrick(e.getContents().getContents())) {
            e.setCancelled(true);
        }
    }

}
