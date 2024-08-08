package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceSmeltListener implements Listener {

    @EventHandler
    public void onBurn(FurnaceBurnEvent e) {
        Material blockType = e.getBlock().getType();
        if(blockType != Material.FURNACE &&
                (Main.getVersionNum() >= 14 && blockType != Material.BLAST_FURNACE) &&
                (Main.getVersionNum() >= 14 && blockType != Material.SMOKER)) {
            return;
        }
        Furnace furnace = (Furnace) e.getBlock().getState();
        ItemStack[] furnaceContents = furnace.getInventory().getContents();
        for(ItemStack itemStack : furnaceContents) {
            if(itemStack == null) {
                continue;
            }

            if (Main.getNBTData(itemStack, "brickthrower_item") != null) {
                e.setCancelled(true);
            }

        }

        // Checking fuel
        if(Main.getNBTData(furnace.getInventory().getFuel(), "brickthrower_item") != null) {
            e.setCancelled(true);
        }
    }
}

