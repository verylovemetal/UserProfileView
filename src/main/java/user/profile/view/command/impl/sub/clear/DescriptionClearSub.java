package user.profile.view.command.impl.sub.clear;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.profile.view.command.type.ISubCommand;
import user.profile.view.command.type.impl.PluginSubCommand;
import user.profile.view.config.provider.ConfigProvider;
import user.profile.view.config.type.CommonConfig;
import user.profile.view.database.async.AsyncDataManager;

import java.util.UUID;

@ISubCommand(argumentsLength = 2, requiredArguments = {"description", "clear"})
public class DescriptionClearSub extends PluginSubCommand {

    public DescriptionClearSub() {
        super("profile");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        ConfigProvider.getConfig(CommonConfig.class).get("messages.clear-description", playerUUID).sendMessage();
        AsyncDataManager.getInstance().updateDataAsync(playerUUID, data -> data.setDescription(""));
    }
}
