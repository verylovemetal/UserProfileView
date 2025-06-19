package user.profile.view.listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import user.profile.view.menu.impl.PlayerInfoMenu;

import java.util.UUID;

public class PlayerShowInfoListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!player.isSneaking()) return;
        Player clickedPlayer = (Player) event.getRightClicked();

        UUID clickedPlayerUUID = clickedPlayer.getUniqueId();
        if (playerUUID.equals(clickedPlayerUUID)) return;

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(79, 250, 130), 1.0f);
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
        player.spawnParticle(Particle.DUST, clickedPlayer.getLocation(), 25, 0.6, 0.6, 0.6, 10, dustOptions);

        new PlayerInfoMenu(playerUUID, clickedPlayerUUID).openInventory();
    }
}
