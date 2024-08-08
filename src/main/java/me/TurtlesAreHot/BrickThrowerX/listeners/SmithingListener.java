package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;

public class SmithingListener implements Listener {
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
    public void onSmithing(PrepareSmithingEvent e) {
        if(eventUsesBrick(e.getInventory().getContents())) {
            e.setResult(new ItemStack(Material.AIR));
        }
    }
}
