package me.TurtlesAreHot.BrickThrowerX;

import de.tr7zw.nbtapi.NBT;
import me.TurtlesAreHot.BrickThrowerX.commands.BrickThrower;
import me.TurtlesAreHot.BrickThrowerX.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static FileConfiguration config;
    private static String version;
    private static int versionNum;

    @Override
    public void onEnable() {
        reloadVersion();
        setDefaultConfigs();
        registerListeners();

        getCommand("brickthrower").setExecutor(new BrickThrower());
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new PlayerClickListener(), this);
        if(!(config.getBoolean("allow-guis"))) {
            // This is when guis should not be able to use BrickThrower items.
            manager.registerEvents(new PrepareCraftListener(), this);
            manager.registerEvents(new EnchantListener(), this);
            manager.registerEvents(new FurnaceSmeltListener(), this);
            manager.registerEvents(new UtilContentsListener(), this);
            if(!(version.equals("1.8"))) {
                // Fueling for Brewing doesn't exist in 1.8
                manager.registerEvents(new BrewFuelListener(), this);
            }
            if(versionNum >= 16) {
                // Smithing table was added in 1.16!
                manager.registerEvents(new SmithingListener(), this);
            }
            if(versionNum >= 14) {
                // stonecutters, cartography tables, loom tables were added in 1.14!
                manager.registerEvents(new InventoryClickListener(), this);
            }
        }
        if(!(config.getBoolean("allow-interacts"))) {
            manager.registerEvents(new InteractEntityListener(), this);
        }


    }

    public static void reloadCon() {
        config = JavaPlugin.getPlugin(Main.class).getConfig();
    }

    public void reloadVersion() {
        // garbage....
        String server_ver = Bukkit.getVersion();
        String non_fixed_version = Bukkit.getServer().getBukkitVersion().split("-")[0];
        non_fixed_version.replaceAll("_", ".");
        version = non_fixed_version;
        String ver_fix = version.substring(0, version.lastIndexOf("."));
        if(!(ver_fix.equals("1"))) {
            //This is in case the version is like "1.12" or "1.8" etc
            version = ver_fix;
        }

        // Setting the version num so we can use it later.
        String endOfVersion = version.substring(version.indexOf('.') + 1);
        versionNum = 0;
        try {
            versionNum = Integer.parseInt(endOfVersion);
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Gets config file
    public static FileConfiguration getCon() {
        return config;
    }

    // Gets Server version
    public static String getServerVersion() {
        return version;
    }

    // Gets server version number (number after "1.")
    public static int getVersionNum() { return versionNum; }

    public void setDefaultConfigs() {
        this.saveDefaultConfig(); // Creates config.yml if it doesn't exist.
        config = this.getConfig();
        config.addDefault("requires-command", true);
        config.addDefault("bricks-given", 10);
        config.addDefault("reload-enabled", true);
        config.addDefault("item-name", "Heavy Brick");
        List<String> materials = new ArrayList<String>();
        String default_item_mat = "BRICK";

        // Checking for versions 1.12 and below
        if(oldMaterials()) {
            // Version 1.12 or below
            materials.add("CLAY_BRICK");
            materials.add("NETHER_BRICK_ITEM");
            default_item_mat = "CLAY_BRICK";
        }
        else {
            materials.add("BRICK");
            materials.add("NETHER_BRICK");
        }
        config.addDefault("items", materials); // This list contains all of the materials that you can get from /brickthrower get.
        config.addDefault("default-item", default_item_mat); // this is the default item that /brickthrower get will give.
        config.addDefault("item-disappear-time", 2); // time until the item on the ground disappears. Put 0 to disable and allow pickup of the item.
        config.addDefault("item-damage", 0.0D);
        config.addDefault("allow-interacts", false);
        config.addDefault("allow-guis", false);
        config.addDefault("item-velocity-multiplier", 1.0D);
        config.addDefault("kb-velocity-multiplier", 1.0D);

        config.options().copyDefaults(true);
        this.saveConfig();
        reloadCon();

    }

    public static boolean oldMaterials() {
        if(versionNum <= 12) {
            return true;
        }

        return false;
    }

    public static String getNBTData(ItemStack item, String key) {
        if(item == null || item.getData() == null || item.getData().getItemType() == Material.AIR)
            return null;

        String nbt_data = NBT.get(item, nbt -> (String) nbt.getString(key));

        if(nbt_data.isEmpty()) return null;
        else return nbt_data;
    }

    public static ItemStack setNBTData(ItemStack item, String key, String keyData) {
        NBT.modify(item, nbt -> {nbt.setString(key, keyData);});
        return item;
    }

    /**
     * This is for logging info to console (DEBUGGING ONLY)
     * @param info info to log
     * @param p Player passed so we can get the server to send the message to
     */
    public static void logInfo(String info, Player p) {
        p.getServer().getLogger().info(info);
    }

}
