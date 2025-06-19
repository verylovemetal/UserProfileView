package user.profile.view.menu.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import user.profile.view.config.provider.ConfigProvider;
import user.profile.view.config.type.CommonConfig;
import user.profile.view.database.async.AsyncDataManager;
import user.profile.view.database.data.ProfileData;
import user.profile.view.item.ItemBuilder;
import user.profile.view.menu.BaseMenu;
import user.profile.view.menu.button.MenuButton;
import user.profile.view.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class PlayerInfoMenu extends BaseMenu {

    private final UUID targetUUID;

    public PlayerInfoMenu(UUID playerUUID, UUID targetUUID) {
        super(playerUUID);
        this.targetUUID = targetUUID;
    }

    @Override
    public void decorateInventory() {
        CommonConfig commonConfig = ConfigProvider.getConfig(CommonConfig.class);
        ProfileData profileData = AsyncDataManager.getInstance().getCachedData(targetUUID);
        if (profileData == null) {
            inventory.close();
            return;
        }

        String onlineMode = profileData.isOnline()
                ? commonConfig.get("messages.online-mode.online", playerUUID).getAsString()
                : commonConfig.get("messages.online-mode.offline", playerUUID).getAsString();

        String description = profileData.getDescription().isEmpty()
                ? commonConfig.get("messages.no-description", playerUUID).getAsString()
                : profileData.getDescription();

        String url = profileData.getUrl().isEmpty()
                ? commonConfig.get("messages.no-url", playerUUID).getAsString()
                : profileData.getUrl();

        String material = commonConfig.get("menu.info-menu.info-item.material", playerUUID).getAsString();
        String name = commonConfig.get("menu.info-menu.info-item.name", playerUUID)
                .withPlaceholder("%online-mode%", onlineMode)
                .withPlaceholder("%player-name%", profileData.getPlayerName())
                .getAsString();

        List<String> lore = commonConfig.get("menu.info-menu.info-item.lore", playerUUID)
                .withPlaceholder("%description%", description)
                .withPlaceholder("%url%", url)
                .withPlaceholder("%date%", DateUtil.formatDate(profileData.getFirstJoinMS()))
                .getAsList();

        ItemStack infoItem = new ItemBuilder(Material.valueOf(material))
                .setDisplayName(name)
                .setLore(lore)
                .build();

        setItem(13, infoItem);
        IntStream.of(0, 8, 18, 26).forEach(slot -> setItem(slot, Material.ORANGE_STAINED_GLASS_PANE));
        IntStream.of(9, 17).forEach(slot -> setItem(slot, Material.GRAY_STAINED_GLASS_PANE));
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public String getTitle() {
        return ConfigProvider.getConfig(CommonConfig.class).get("menu.info-menu.title", playerUUID).getAsString();
    }
}
