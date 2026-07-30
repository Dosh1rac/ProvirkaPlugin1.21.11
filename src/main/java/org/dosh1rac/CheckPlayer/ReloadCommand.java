package org.dosh1rac.CheckPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final CheckPlayer plugin;

    public ReloadCommand(CheckPlayer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("provirka.reload")) {
            sender.sendMessage("§cУ тебя нет прав.");
            return true;
        }

        plugin.reloadConfig();
        sender.sendMessage("§aКонфиг успешно перезагружен.");
        return true;
    }
}