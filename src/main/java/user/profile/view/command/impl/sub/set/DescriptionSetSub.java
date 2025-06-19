package user.profile.view.command.impl.sub.set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.profile.view.command.type.ISubCommand;
import user.profile.view.command.type.impl.PluginSubCommand;
import user.profile.view.config.provider.ConfigProvider;
import user.profile.view.config.type.CommonConfig;
import user.profile.view.database.async.AsyncDataManager;

import java.util.Arrays;
import java.util.UUID;

@ISubCommand(argumentsLength = 3, requiredArguments = {"description", "set"})
public class DescriptionSetSub extends PluginSubCommand {

    private static final String ALLOWED_PATTERN = "^[a-zA-Zа-яА-Я0-9 .,!?_\\-]+$";

    public DescriptionSetSub() {
        super("profile");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        CommonConfig commonConfig = ConfigProvider.getConfig(CommonConfig.class);
        if (args.length < 3) {
            commonConfig.get("messages.description-is-empty", playerUUID).sendMessage();
            return;
        }

        String description = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        int descriptionLengthLimit = commonConfig.get("description.length-limit", playerUUID).getAsInt();

        if (description.length() > descriptionLengthLimit) {
            commonConfig.get("messages.description-length-limit", playerUUID).sendMessage();
            return;
        }

        if (!description.matches(ALLOWED_PATTERN)) {
            commonConfig.get("messages.description-invalid-characters", playerUUID).sendMessage();
            return;
        }

        commonConfig.get("messages.set-description", playerUUID).sendMessage();
        AsyncDataManager.getInstance().updateDataAsync(playerUUID, data -> data.setDescription(description));
    }
}
