package user.profile.view.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import user.profile.view.item.ItemBuilder;
import user.profile.view.menu.button.MenuButton;
import user.profile.view.menu.tracker.MenuTracker;
import user.profile.view.util.ChatUtil;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class BaseMenu implements IMenu {

    private final Map<Integer, MenuButton> buttons = new HashMap<>();

    protected final UUID playerUUID;

    protected Inventory inventory;

    public BaseMenu(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void openInventory() {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        inventory = Bukkit.createInventory(null, getSize(), ChatUtil.format(getTitle()));
        decorateInventory();

        player.openInventory(inventory);
        MenuTracker.getInstance().trackMenu(playerUUID, this);
    }

    public void closeInventory() {
        MenuTracker.getInstance().untrackMenu(playerUUID);
    }

    public void addButton(int slot, MenuButton button) {
        setItem(slot, button.getItemIcon());
        buttons.put(slot, button);
    }

    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }

    public void setItem(int slot, Material material) {
        inventory.setItem(slot, new ItemBuilder(material).setDisplayName("").build());
    }

    public void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    public abstract void decorateInventory();
}
