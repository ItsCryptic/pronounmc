package io.github.acekironcommunity.pronounmc.commands;

import io.github.acekironcommunity.pronounmc.MyPlugin;
import io.github.acekironcommunity.pronounmc.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PMCReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MyPlugin.Reload(); // Reset PronounAPI, command executors and tabcompleters
        Utils.ReloadConfig(); // Reload message-prefix, verbose-logging, enable-third-party-override, third-party-override

        return true;
    }

}
