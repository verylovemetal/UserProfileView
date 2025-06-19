package user.profile.view.listener.data;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import user.profile.view.database.async.AsyncDataManager;

import java.util.UUID;

public class ProfileDataLoadListener implements Listener {

    @EventHandler
    public void onLoginEvent(AsyncPlayerPreLoginEvent event) {
        UUID playerUUID = event.getUniqueId();
        PlayerProfile playerProfile = event.getPlayerProfile();

        AsyncDataManager.getInstance().loadData(playerUUID, playerProfile.getName());
        setDataSettings(playerUUID, playerProfile);
    }

    private void setDataSettings(UUID playerUUID, PlayerProfile profile) {
        AsyncDataManager.getInstance().updateData(playerUUID, data -> {
            data.setPlayerName(profile.getName());
            data.setOnline(true);
        });
    }
}
