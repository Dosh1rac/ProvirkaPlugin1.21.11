package org.dosh1rac.CheckPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ProvirkaCommand implements CommandExecutor {
    private final CheckPlayer plugin;

    public ProvirkaCommand(CheckPlayer plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!sender.hasPermission("provirka.use")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no_permission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConfig().getString("messages.usage-check"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(plugin.getConfig().getString("messages.player-not-found"));
            return true;
        }

        if (ProvirkaListener.checkingPlayers.contains(target.getUniqueId())) {
            sender.sendMessage(plugin.getConfig().getString("messages.already"));
            return true;
        }

        ProvirkaListener.checkingPlayers.add(target.getUniqueId());


        int repeat = plugin.getConfig().getInt("check.repeat");
        int interval = plugin.getConfig().getInt("check.interval");

        new BukkitRunnable() {
            private int count;

            @Override
            public void run() {
                if (count >= repeat) {
                    cancel();
                    return;
                }
                for (String message : plugin.getConfig().getStringList("messages.check")) {
                    target.sendMessage(message);
                }
                String title = plugin.getConfig().getString("title.title");
                String subtitle = plugin.getConfig().getString("title.subtitle");
                target.sendTitle(title, subtitle, 10, 100, 20);
                count++;
            }
        }.runTaskTimer(plugin, 0L, interval);

        sender.sendMessage("§aИгрок " + target.getName() + " вызван на проверку.");

        int blindnessDuration = plugin.getConfig().getInt("effects.blindness.duration");
        int blindnessAmplifier = plugin.getConfig().getInt("effects.blindness.amplifier");

        int nauseaDuration = plugin.getConfig().getInt("effects.nausea.duration");
        int nauseaAmplifier = plugin.getConfig().getInt("effects.nausea.amplifier");

        target.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.BLINDNESS,
                        blindnessDuration,
                        blindnessAmplifier
                )
        );

        target.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.NAUSEA,
                        nauseaDuration,
                        nauseaAmplifier
                )
        );

        target.playSound(target.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);

        return true;
    }
    
}
