package org.dosh1rac.CheckPlayer;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

    if (!ProvirkaListener.checkingPlayers.contains((player.getUniqueId()))){
        return;
    }

    ProvirkaListener.checkingPlayers.remove(player.getUniqueId());

        Bukkit.getBanList(BanList.Type.NAME)
                .addBan(player.getName(),
                    "Выход с сервера во время проверки",
                    null,
                    "PlayerCheck");

        Bukkit.broadcastMessage("§cИгрок " + player.getName() + " вышел во время проверки и был забанен.");

    }

}
