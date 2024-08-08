package me.TurtlesAreHot.BrickThrowerX.listeners;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewingStandFuelEvent;

public class BrewFuelListener implements Listener {

    @EventHandler
    public void onBrewFuel(BrewingStandFuelEvent e) {
        if(Main.getNBTData(e.getFuel(), "brickthrower_item") != null) {
            e.setCancelled(true);
        }
    }
}
