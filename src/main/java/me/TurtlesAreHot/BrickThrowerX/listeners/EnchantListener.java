package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantListener implements Listener {

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e) {
        if(Main.getNBTData(e.getItem(), "brickthrower_item") != null) {
            e.setCancelled(true);
            return;
        }

        // Checking Lapis
        if(Main.oldMaterials()) {
            // Not something we need to check on old versions
           return;
        }

        ItemStack[] inv = e.getInventory().getContents();

        for (ItemStack itemStack : inv) {
            if (itemStack == null) {
                continue;
            }
            if (itemStack.getType() != Material.LAPIS_LAZULI) {
                continue;
            }
            if (Main.getNBTData(itemStack, "brickthrower_item") != null) {
                e.setCancelled(true);
            }
        }

    }
}
