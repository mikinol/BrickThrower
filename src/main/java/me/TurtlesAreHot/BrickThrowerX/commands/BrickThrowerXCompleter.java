package me.TurtlesAreHot.BrickThrowerX.commands;

import me.TurtlesAreHot.BrickThrowerX.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BrickThrowerXCompleter implements TabCompleter {
    /**
     * This is called when a player types a command and presses TAB.
     * @param sender The entity that pressed TAB (can be console or a player).
     * @param command The command that was sent
     * @param label The label for the command
     * @param args arguments for the command.
     * @return The list of variants of commands.
     */
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> options = Arrays.asList("get", "list");
            if(Main.getCon().getBoolean("reload-enabled")) options.add("reload");
            return filterOptions(options, args[0]);
        }

        if (args.length == 2 && args[0].equals("get")) {
            return filterOptions(Main.getCon().getStringList("items"), args[1]);
        }

        return new ArrayList<>();
    }

    /**
     * Filters the given list of options based on the input given.
     * @param options The list of options to filter.
     * @param input The input to filter by.
     * @return The filtered list.
     */
    private List<String> filterOptions(List<String> options, String input) {
        return options.stream()
                .filter(option -> option.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }
}

