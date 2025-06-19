package user.profile.view;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import user.profile.view.command.CommandController;
import user.profile.view.command.impl.ProfileCommand;
import user.profile.view.command.type.impl.PluginSuperCommand;
import user.profile.view.config.AbstractConfig;
import user.profile.view.config.registry.ConfigRegistry;
import user.profile.view.config.type.CommonConfig;
import user.profile.view.database.DataStorage;
import user.profile.view.listener.PlayerShowInfoListener;
import user.profile.view.listener.data.ProfileDataLoadListener;
import user.profile.view.listener.data.ProfileDataUnloadListener;
import user.profile.view.menu.listener.MenuListener;

import java.util.List;
import java.util.concurrent.Executor;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private final String collectionName = "users";
    private final Executor mainExecutor = Bukkit.getScheduler().getMainThreadExecutor(this);

    @Override
    public void onEnable() {
        instance = this;
        registerListener();
        ConfigRegistry.getInstance().registerDefaultConfigs();

        CommandController commandController = CommandController.getInstance();
        registerCommands().forEach(commandController::registerCommand);

        DataStorage.getInstance().initializeDatabase(collectionName);
    }

    @Override
    public void onDisable() {
        DataStorage.getInstance().close();
    }

    private List<PluginSuperCommand> registerCommands() {
        return List.of(
                new ProfileCommand(this)
        );
    }

    private void registerListener() {
        List.of(
                new ProfileDataLoadListener(),
                new ProfileDataUnloadListener(),
                new MenuListener(),
                new PlayerShowInfoListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    public List<AbstractConfig> getConfigurations() {
        return List.of(
                new CommonConfig()
        );
    }
}
