package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;

public class SmithingListener implements Listener {
    public boolean eventUsesBrick(ItemStack[] contents) {
        for (ItemStack content : contents) {
            if (content == null) {
                continue;
            }
            if (Main.getNBTData(content, "brickthrower_item") != null) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onSmithing(PrepareSmithingEvent e) {
        if(eventUsesBrick(e.getInventory().getContents())) {
            e.setResult(new ItemStack(Material.AIR));
        }
    }
}
