package org.dosh1rac.CheckPlayer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProvirkaListener implements Listener {

    public static final Set<UUID> checkingPlayers = new HashSet<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!checkingPlayers.contains(player.getUniqueId())) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) return;

        if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {

            Location freezeLocation = from.clone();
            freezeLocation.setYaw(to.getYaw());
            freezeLocation.setPitch(to.getPitch());

            event.setTo(freezeLocation);
        }
    }
}
