package user.profile.view.command.impl.sub.set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.profile.view.command.type.ISubCommand;
import user.profile.view.command.type.impl.PluginSubCommand;
import user.profile.view.config.provider.ConfigProvider;
import user.profile.view.config.type.CommonConfig;
import user.profile.view.database.async.AsyncDataManager;

import java.util.UUID;

@ISubCommand(argumentsLength = 3, requiredArguments = {"url", "set"})
public class UrlSetSub extends PluginSubCommand {

    public UrlSetSub() {
        super("profile");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        CommonConfig commonConfig = ConfigProvider.getConfig(CommonConfig.class);
        if (args.length < 3) {
            commonConfig.get("messages.url-is-empty", playerUUID).sendMessage();
            return;
        }

        String url = args[2];
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            commonConfig.get("messages.url-wrong-format", playerUUID).sendMessage();
            return;
        }

        commonConfig.get("messages.set-url", playerUUID).sendMessage();
        AsyncDataManager.getInstance().updateDataAsync(playerUUID, data -> data.setUrl(url));
    }
}
