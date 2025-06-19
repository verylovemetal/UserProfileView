package user.profile.view.menu.tracker;

import lombok.Getter;
import user.profile.view.menu.BaseMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuTracker {

    @Getter
    private static final MenuTracker instance = new MenuTracker();

    private final Map<UUID, BaseMenu> menus = new HashMap<>();

    public void trackMenu(UUID playerUUID, BaseMenu menu) {
        menus.put(playerUUID, menu);
    }

    public BaseMenu getMenu(UUID playerUUID) {
        return menus.get(playerUUID);
    }

    public void untrackMenu(UUID playerUUID) {
        menus.remove(playerUUID);
    }
}
