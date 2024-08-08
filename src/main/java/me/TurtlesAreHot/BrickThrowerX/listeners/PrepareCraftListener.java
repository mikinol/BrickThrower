package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareCraftListener implements Listener {

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        for(HumanEntity entity : e.getViewers()) {
            if(!(entity instanceof Player)) {
                continue;
            }
            for(ItemStack item : e.getInventory().getContents()) {
                if(Main.getNBTData(item, "brickthrower_item") != null) {
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                    // Break out of loop to stop pointless loops after result already set.
                    break;
                }
            }
        }
    }
}
