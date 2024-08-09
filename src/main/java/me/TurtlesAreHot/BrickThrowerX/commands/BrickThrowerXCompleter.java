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
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return filterOptions(Arrays.asList("get", "reload", "list"), args[0]);
        }

        if (args.length == 2 && args[0].equals("get")) {
            return filterOptions(Main.getCon().getStringList("items"), args[1]);
        }

        return new ArrayList<>();
    }

    private List<String> filterOptions(List<String> options, String input) {
        return options.stream()
                .filter(option -> option.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }
}

