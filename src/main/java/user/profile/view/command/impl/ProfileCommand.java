package user.profile.view.command.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.profile.view.Main;
import user.profile.view.command.impl.sub.clear.DescriptionClearSub;
import user.profile.view.command.impl.sub.clear.UrlClearSub;
import user.profile.view.command.impl.sub.set.DescriptionSetSub;
import user.profile.view.command.impl.sub.set.UrlSetSub;
import user.profile.view.command.type.ICommand;
import user.profile.view.command.type.impl.PluginSuperCommand;
import user.profile.view.config.provider.ConfigProvider;
import user.profile.view.config.type.CommonConfig;
import user.profile.view.database.async.AsyncDataManager;
import user.profile.view.database.data.ProfileData;
import user.profile.view.util.DateUtil;

import java.util.UUID;

@ICommand(aliases = "")
public class ProfileCommand extends PluginSuperCommand {

    public ProfileCommand(Main plugin) {
        super("profile", plugin);
        registerSubCommand(new DescriptionSetSub());
        registerSubCommand(new UrlSetSub());
        registerSubCommand(new DescriptionClearSub());
        registerSubCommand(new UrlClearSub());
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        CommonConfig commonConfig = ConfigProvider.getConfig(CommonConfig.class);
        if (args.length < 1) {
            commonConfig.get("messages.profile-command-usage", playerUUID).sendMessage();
            return;
        }

        String playerName = args[0];
        AsyncDataManager asyncDataManager = AsyncDataManager.getInstance();
        if (playerName.equalsIgnoreCase(player.getName())) {
            ProfileData profileData = asyncDataManager.getCachedData(playerUUID);
            if (profileData == null) return;

            sendInfoMessage(playerUUID, profileData);
            return;
        }

        asyncDataManager.getDataByNameAsync(playerName).thenAcceptAsync(data -> {
            if (data == null) {
                commonConfig.get("messages.player-not-found", playerUUID).sendMessage();
                return;
            }

            sendInfoMessage(playerUUID, data);
        }, Main.getInstance().getMainExecutor());
    }

    private void sendInfoMessage(UUID senderUUID, ProfileData profileData) {
        CommonConfig commonConfig = ConfigProvider.getConfig(CommonConfig.class);

        String onlineMode = profileData.isOnline()
                ? commonConfig.get("messages.online-mode.online", senderUUID).getAsString()
                : commonConfig.get("messages.online-mode.offline", senderUUID).getAsString();

        String description = profileData.getDescription().isEmpty()
                ? commonConfig.get("messages.no-description", senderUUID).getAsString()
                : profileData.getDescription();

        String url = profileData.getUrl().isEmpty()
                ? commonConfig.get("messages.no-url", senderUUID).getAsString()
                : profileData.getUrl();

        commonConfig.get("messages.info-message", senderUUID)
                .withPlaceholder("%player-name%", profileData.getPlayerName())
                .withPlaceholder("%online-mode%", onlineMode)
                .withPlaceholder("%description%", description)
                .withPlaceholder("%url%", url)
                .withPlaceholder("%date%", DateUtil.formatDate(profileData.getFirstJoinMS()))
                .sendMessage();

    }
}
