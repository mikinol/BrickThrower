package me.TurtlesAreHot.BrickThrowerX.commands;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BrickThrower implements CommandExecutor {

    /**
     * This is executed when the command is inputted by a sender
     * @param sender The entity that sent the message (can be console or a player)
     * @param cmd The command that was sent
     * @param label The label for the command
     * @param args arguments for the command.
     * @return Executes the given command, returning its success.
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            // Checking for player not sender.
            sender.sendMessage(Main.getPhrase("only_player_can_run"));
            return false;
        }

        Player p = (Player) sender;
        if(!(label.equalsIgnoreCase("brickthrower")) && !(label.equalsIgnoreCase("brth"))) {
            msgPlayer(p, Main.getPhrase("non_supported_command"));
            return false;
        }

        if(args.length == 0) {
            return infoCmd(p);
        }

        /*
         * Switch case for the first argument.
         * This checks what command you are running.
         * Switches it to lower case so caps don't matter
         */
        switch(args[0].toLowerCase()) {
            case "get":
                return getCmd(p, args);
            case "reload":
                return reloadCmd(p);
            case "list":
                return listCmd(p);
            default:
                return infoCmd(p);
        }
    }

    /**
     * Sends a message to a player in a certain format.
     * @param p Player sending the message to.
     * @param message The message to send.
     */
    public void msgPlayer(Player p, String message) {
        p.sendMessage(Main.getPhrase("msg_player").replace("%message%", message));
    }

    /**
     * Checks if a player has a permission, if they don't it tells them they have no permission.
     * @param player Player we are checking for permissions for
     * @param command The command we are checking if they have permissions for.
     * @return true if player has permission, false if not.
     */
    public boolean hasPermission(Player player, String command) {
        // Checking if the player has all permissions
        if (player.hasPermission("brickthrower.*")) {
            return true;
        }
        boolean perm = player.hasPermission("brickThrower." + command);
        if (!perm) {
            msgPlayer(player, Main.getPhrase("no_permission").replace("%command%", command));
        }

        return perm;
    }

    /**
     * The /brickthrower get command
     * @param player The player who executed the command
     * @param args The extra arguments given to the command
     * @return Returns if the command ran
     */
    public boolean getCmd(Player player, String[] args) {
        if(!(hasPermission(player, "get"))) {
            return false;
        }
        Material itemMaterial = null;

        /*
         * Assigning the material for the brick.
         * First checking for permissions for this though.
         */
        if(args.length > 1) {
            // given item
            if (!(hasPermission(player, "getother"))) {
                return false;
            }
            List<String> items = Main.getCon().getStringList("items");
            String arg2 = args[1];
            itemMaterial = Material.getMaterial(arg2.toUpperCase());
            if(itemMaterial == null) {
                msgPlayer(player, Main.getPhrase("item_does_not_exist") +
                        Main.getPhrase("list_all_allowed_items"));
                return false;
            }
            if(!(items.contains(arg2.toUpperCase()))) {
                msgPlayer(player, Main.getPhrase("item_not_in_config_allowed_list") +
                        Main.getPhrase("list_all_allowed_items"));
                return false;
            }

        }
        else {
            // default item
            if(Main.oldMaterials()) {
               itemMaterial = Material.getMaterial("CLAY_BRICK");
            }
            else {
                itemMaterial = Material.BRICK;
            }
        }


        // Creating ItemStack for the item to give
        ItemStack brick = new ItemStack(itemMaterial, Main.getCon().getInt("bricks-given"));
        ItemMeta brickMeta = brick.getItemMeta();
        brickMeta.setDisplayName(Main.getCon().getString("item-name"));
        brick.setItemMeta(brickMeta);

        if(Bukkit.getPluginManager().isPluginEnabled("NBTAPI")) {
            // Setting NBT data so plugin knows this is a BrickThrower item.
            brick = Main.setNBTData(brick, "brickthrower_item", "true");
        }

        // Giving the player the items.
        if(player.getInventory().firstEmpty() == -1) {
            msgPlayer(player, Main.getPhrase("no_space_for_item"));
            return false;
        }
        player.getInventory().addItem(brick);
        return true;
    }

    /**
     * The /brickthrower reload command
     * @param player The player who executed the command
     * @return Returns if the command ran
     */
    public boolean reloadCmd(Player player) {
        if(!(hasPermission(player, "reload"))) {
            return false;
        }
        if(!(Main.getCon().getBoolean("reload-enabled"))) {
            msgPlayer(player, Main.getPhrase("reload_in_not_enabled"));
            return false;
        }

        JavaPlugin.getPlugin(Main.class).reloadConfig();
        Main.reloadCon();
        msgPlayer(player, Main.getPhrase("successfully_reloaded"));
        return true;
    }

    /**
     * The /brickthrower list command
     * @param player The player who executed the command
     * @return Returns if the command ran
     */
    public boolean listCmd(Player player) {
        if(!(hasPermission(player, "list"))) {
            return false;
        }

        msgPlayer(player, Main.getPhrase("valid_materials_list"));
        for(String mat : Main.getCon().getStringList("items")) {
            player.sendMessage(Main.getPhrase("valid_materials_item").replace("%material%", mat));
        }

        return true;
    }

    /**
     * The /brickthrower info command
     * @param player The player who executed the command
     * @return Returns if the command ran
     */
    public boolean infoCmd(Player player) {
        if(!(hasPermission(player, "info"))) {
            return false;
        }
        // Pulling information from plugin.yml for this plugin
        PluginDescriptionFile pdf = JavaPlugin.getPlugin(Main.class).getDescription();
        // Doesn't use msgPlayer function since its a bunch of info (don't want spam)
        player.sendMessage(Main.getPhrase("version").replace("%version%", pdf.getVersion()));
        player.sendMessage(Main.getPhrase("created_by").replace("%authors%", pdf.getAuthors().get(0)));
        player.sendMessage(Main.getPhrase("description").replace("%description%", pdf.getDescription()));
        player.sendMessage(Main.getPhrase("commands"));
        player.sendMessage(Main.getPhrase("parameter_legend"));
        player.sendMessage(Main.getPhrase("command_brickthrower"));
        player.sendMessage(Main.getPhrase("command_brickthrower_get"));
        if(Main.getCon().getBoolean("reload-enabled")) player.sendMessage(Main.getPhrase("command_brickthrower_reload"));
        player.sendMessage(Main.getPhrase("command_brickthrower_list"));
        return true;
    }
}
