package me.TurtlesAreHot.BrickThrowerX;

import de.tr7zw.nbtapi.NBT;
import me.TurtlesAreHot.BrickThrowerX.commands.BrickThrower;
import me.TurtlesAreHot.BrickThrowerX.commands.BrickThrowerXCompleter;
import me.TurtlesAreHot.BrickThrowerX.listeners.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static FileConfiguration config;
    private static FileConfiguration lang;
    private static FileConfiguration defaultLang;
    private static FileConfiguration defaultEnglishLang;
    private static String version;
    private static int versionNum;

    /**
     * This method is called when the plugin is enabled.
     * It is responsible for reloading the version, setting default configs,
     * registering listeners, and registering the brickthrower command.
     */
    @Override
    public void onEnable() {
        reloadVersion();
        setDefaultConfigs();

        if(!config.getBoolean("allow-throw-without-nbt-tag") && !Bukkit.getPluginManager().isPluginEnabled("NBTAPI")) {
            this.getLogger().severe("BrickThrowerX needs NBTAPI to use bricks with nbt, but it is not enabled. Disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        registerListeners();
        checkPluginVersion();
        new Metrics(this, 23210);

        getCommand("brickthrower").setExecutor(new BrickThrower());
        getCommand("brickthrower").setTabCompleter(new BrickThrowerXCompleter());
    }

    private void checkPluginVersion() {
        try {
            // URL к вашему файлу с последним релизом на GitHub
            URL url = new URL("https://api.github.com/repos/mikinol/BrickThrowerX/releases/latest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();


            String latestVersion = content.toString().split("\"tag_name\":\"")[1].split("\"")[0];
            if (!this.getDescription().getVersion().equals(latestVersion)) {
                getLogger().info("New update available: " + latestVersion);
            } else {
                getLogger().info("Your plugin is up to date.");
            }

        } catch (Exception e) {
            getLogger().warning("Failed to check for updates: " + e.getMessage());
        }
    }

    /**
     * Registers all listeners for the plugin.
     * The listeners are only registered if the corresponding
     * setting in the config file is enabled.
     */
    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new PlayerClickListener(), this);

        if(!(config.getBoolean("allow-guis-with-nbt-item")) && !(config.getBoolean("allow-throw-without-nbt-tag"))) {
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

    /**
     * Reloads the configuration and language files.
     */
    public static void reloadCon() {
        Plugin plugin = JavaPlugin.getPlugin(Main.class);
        config = plugin.getConfig();

        File folder = new File(plugin.getDataFolder() + "/lang");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        loadLang("lang/en.yml", folder + "/en.yml");
        loadLang("lang/ru.yml", folder + "/ru.yml");

        String langPath = "/lang/" + config.getString("language") + ".yml";
        lang = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + langPath));

        InputStream defaultLang = plugin.getResource(langPath);
        if(defaultLang != null) {
            Main.defaultLang = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultLang)
            );
        }

        InputStream englishLang = plugin.getResource("/lang/en.yml");
        if(englishLang != null) {
            Main.defaultEnglishLang = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(englishLang)
            );
        }
    }

    /**
     * Copies the language file from the jar to the plugin's data folder if
     * the file does not already exist.
     * @param pathfrom the path of the file in the jar
     * @param pathto the path of the file in the plugin's data folder
     */
    private static void loadLang(String pathfrom, String pathto){
        File langFile = new File(pathto);
        if(!langFile.exists()) {
            try {
                Files.copy(JavaPlugin.getPlugin(Main.class).getResource(pathfrom), langFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reloads the version of the server by extracting it from {@link Bukkit#getVersion()}.
     * This method is used to get the server version number and store it in the {@link Main#version} field.
     * The version number is then used to check if certain events should be registered.
     * <p>
     * This method also sets the {@link Main#versionNum} field which is used to check if certain events should be registered.
     */
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

    /**
     * Gets the config file.
     * @return The config file.
     */
    public static FileConfiguration getCon() {
        return config;
    }

    /**
     * Gets the language file.
     * @return The language file.
     */
    public static FileConfiguration getLang() {
        return lang;
    }

    /**
     * Gets the default language file.
     * @return The default language file.
     */
    public static FileConfiguration getDefaultLang() {
        return defaultLang;
    }

    /**
     * Gets the default english language file.
     * @return The default english language file.
     */
    public static FileConfiguration getDefaultEnglishLang() {
        return defaultEnglishLang;
    }

    /**
     * Gets phrase from language file.
     * @param phrase The phrase to get from the language file.
     * @return The phrase from the language file.
     */
    public static String getPhrase(String phrase) {
        String phraseFromLang = lang.getString(phrase);

        if (phraseFromLang == null) {
            phraseFromLang = defaultLang.getString(phrase);
        }
        if (phraseFromLang == null) {
            phraseFromLang = defaultEnglishLang.getString(phrase);
        }
        if (phraseFromLang == null) {
            phraseFromLang = phrase;
        }

        return ChatColor.translateAlternateColorCodes('&', phraseFromLang);
    }

    /**
     * Gets the server version.
     * @return The server version.
     */
    public static String getServerVersion() {
        return version;
    }

    /**
     * Gets server version number (number after "1.")
     * @return Server version number
     */
    public static int getVersionNum() { return versionNum; }

    /**
     * Adds default values of config
     */
    public void setDefaultConfigs() {
        this.saveDefaultConfig(); // Creates config.yml if it doesn't exist.
        config = this.getConfig();
        config.addDefault("bricks-given", 10);
        config.addDefault("reload-enabled", true);
        config.addDefault("item-name", "Heavy Brick");
        List<String> materials = new ArrayList<>();
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
        config.addDefault("item-disappear-time", 3); // time until the item on the ground disappears. Put 0 to disable and allow pickup of the item.
        config.addDefault("item-damage", 4.0D);
        config.addDefault("allow-interacts", false);
        config.addDefault("allow-guis-with-nbt-item", false);
        config.addDefault("item-velocity-multiplier", 1.0D);
        config.addDefault("kb-velocity-multiplier", 1.0D);
        config.addDefault("language", "en");
        config.addDefault("allow-throw-without-nbt-tag", true);

        config.options().copyDefaults(true);
        this.saveConfig();
        reloadCon();

    }

    /**
     * Checks if the server has old materials (1.12 or below)
     * @return true if the server is 1.12 or below
     */
    public static boolean oldMaterials() {
        return versionNum <= 12;
    }

    /**
     * Gets NBT data from an item.
     * @param item the itemstack to get the NBT data
     * @param key the key to get data
     * @return the NBT data or null if it doesn't exist
     */
    public static String getNBTData(ItemStack item, String key) {
        if(item == null || item.getAmount() == 0)
            return null;

        String nbt_data = NBT.get(item, nbt -> (String) nbt.getString(key));

        if(nbt_data.isEmpty()) return null;
        else return nbt_data;
    }

    /**
     * Sets NBT data from an item.
     * @param item the itemstack to set the NBT data
     * @param key the key to set data
     * @param keyData the data to set
     * @return the itemstack with the NBT data
     */
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
