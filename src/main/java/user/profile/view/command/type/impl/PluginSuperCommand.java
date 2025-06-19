package user.profile.view.command.type.impl;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.NotNull;
import user.profile.view.Main;
import user.profile.view.command.AbstractCommand;
import user.profile.view.command.CommandController;
import user.profile.view.command.type.ICommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class PluginSuperCommand extends AbstractCommand {

    private final String commandName;
    protected final Main plugin;
    private final ICommand iCommand;
    private final String[] commandAliases;

    @Getter
    private final List<PluginSubCommand> subCommands = new ArrayList<>();

    public PluginSuperCommand(String commandName, Main plugin) {
        super(commandName, plugin);

        this.commandName = commandName;
        this.plugin = plugin;
        this.iCommand = getClass().getAnnotation(ICommand.class);
        this.commandAliases = iCommand.aliases();
        registerCommand();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        CommandController commandController = CommandController.getInstance();
        commandController.getCommand(commandName).ifPresent(command -> {
            Optional<PluginSubCommand> pluginSubCommand = commandController.getSubCommand(command, args);
            if (pluginSubCommand.isPresent()) {
                pluginSubCommand.get().onCommand(sender, args);
            } else {
                attemptRunCommand(sender, args);
            }
        });

        return true;
    }

    public void registerCommand() {
        List<String> aliases = Arrays.stream(iCommand.aliases())
                .filter(alias -> !alias.isEmpty())
                .toList();

        setAliases(aliases);

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register(commandName, "userprofile", this);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void registerSubCommand(PluginSubCommand pluginSubCommand) {
        subCommands.add(pluginSubCommand);
    }
}
