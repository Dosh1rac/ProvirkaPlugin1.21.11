package org.dosh1rac.CheckPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class ProvirkaStopCommand implements CommandExecutor {
    private final CheckPlayer plugin;
    public ProvirkaStopCommand(CheckPlayer plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("provirka.stop")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no_permission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConfig().getString("messages.usage-stop"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(plugin.getConfig().getString("messages.player-not-found"));
            return true;
        }

        if (!ProvirkaListener.checkingPlayers.contains(target.getUniqueId())) {
            sender.sendMessage(plugin.getConfig().getString("messages.not_checking"));
            return true;
        }
        ProvirkaListener.checkingPlayers.remove(target.getUniqueId());

        target.removePotionEffect(PotionEffectType.NAUSEA);
        target.removePotionEffect(PotionEffectType.BLINDNESS);

        target.setWalkSpeed(0.2f);
        target.setFlySpeed(0.1f);

        target.sendMessage(plugin.getConfig().getString("messages.stop"));

        sender.sendMessage(
                plugin.getConfig()
                        .getString("messages.stopped")
                        .replace("%player%", target.getName())
        );

        return true;
    }
}