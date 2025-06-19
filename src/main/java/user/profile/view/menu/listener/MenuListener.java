package user.profile.view.menu.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import user.profile.view.menu.BaseMenu;
import user.profile.view.menu.button.MenuButton;
import user.profile.view.menu.tracker.MenuTracker;

import java.util.UUID;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        BaseMenu baseMenu = MenuTracker.getInstance().getMenu(playerUUID);
        if (baseMenu == null) return;

        Inventory eventInventory = event.getInventory();
        Inventory trackedInventory = baseMenu.getInventory();

        if (!eventInventory.equals(trackedInventory)) return;
        event.setCancelled(true);

        MenuButton clickedButton = baseMenu.getButton(event.getSlot());
        if (clickedButton == null) return;

        clickedButton.onClick(player);
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        BaseMenu baseMenu = MenuTracker.getInstance().getMenu(playerUUID);
        if (baseMenu == null) return;

        baseMenu.closeInventory();
    }
}
