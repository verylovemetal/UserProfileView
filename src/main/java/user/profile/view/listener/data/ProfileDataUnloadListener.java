package user.profile.view.listener.data;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import user.profile.view.database.async.AsyncDataManager;

import java.util.UUID;

public class ProfileDataUnloadListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        AsyncDataManager.getInstance().invalidateCache(playerUUID);
        setDataSettingsAsync(playerUUID);
    }

    private void setDataSettingsAsync(UUID playerUUID) {
        AsyncDataManager.getInstance().updateDataAsync(playerUUID, data -> {
            data.setOnline(false);
        });
    }
}
