package org.dosh1rac.CheckPlayer;

import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public final class CheckPlayer extends JavaPlugin {



    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info(("плагин запустился"));
        this.getCommand("provirka").setExecutor(new ProvirkaCommand(this));
        getCommand("provirkastop").setExecutor(new ProvirkaStopCommand(this));
        getCommand("checkreload").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager()
                .registerEvents(new ProvirkaListener(), this);
    }



    @Override
    public void onDisable() {
        getServer().broadcast(Component.text("плагин выключился"), "group.admin");

    }
}
