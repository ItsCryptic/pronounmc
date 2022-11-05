package io.github.acekironcommunity.pronounmc;

import io.github.acekironcommunity.pronounmc.commands.AddPronounCommand;
import io.github.acekironcommunity.pronounmc.commands.GetPronounsCommand;
import io.github.acekironcommunity.pronounmc.commands.PMCReloadCommand;
import io.github.acekironcommunity.pronounmc.commands.RemovePronounCommand;
import io.github.acekironcommunity.pronounmc.commands.UnusedCommand;
import io.github.acekironcommunity.pronounmc.handlers.ChatHandler;
import io.github.acekironcommunity.pronounmc.tabcompleters.PronounsTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyPlugin extends JavaPlugin {

    public static PronounAPI papi;

    @Override
    public void onEnable() {
        // Initialize the config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        Utils.SetPlugin(this);

        Utils.log("Starting up", true);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Utils.log("Hooking into PlaceholderAPI", false);
            new PronounMCPlaceholder().register();
        }

        if (getConfig().getBoolean("handle-chat")) new ChatHandler(this);

        Reload();
    }

    public static void Reload() {
        MyPlugin instance = Utils.GetPlugin();

        MyPlugin.papi = new PronounAPI(instance);

        PronounsTabCompleter pronounsTabCompleter = new PronounsTabCompleter();

        boolean pronounOverrideEnabled = Utils.getPronounOverrideEnabled();

        instance.getCommand("getpronouns").setExecutor(new GetPronounsCommand());
        instance.getCommand("pmcreload").setExecutor(new PMCReloadCommand());

        if (pronounOverrideEnabled) {
            UnusedCommand cmdHandler = new UnusedCommand();
            
            instance.getCommand("addpronoun").setExecutor(cmdHandler);
            instance.getCommand("removepronoun").setExecutor(cmdHandler);
        } else {
            instance.getCommand("addpronoun").setExecutor(new AddPronounCommand());
            instance.getCommand("addpronoun").setTabCompleter(pronounsTabCompleter);

            instance.getCommand("removepronoun").setExecutor(new RemovePronounCommand());
            instance.getCommand("removepronoun").setTabCompleter(pronounsTabCompleter);
        }
    }

    @Override
    public void onDisable() {
        Utils.log("Shutting down", true);
    }
}
