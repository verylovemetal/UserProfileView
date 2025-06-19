package user.profile.view.command;


import lombok.Getter;
import org.bukkit.command.CommandSender;
import user.profile.view.command.type.impl.PluginSubCommand;
import user.profile.view.command.type.impl.PluginSuperCommand;

import java.util.*;

@Getter
public class CommandController {

    @Getter
    private static final CommandController instance = new CommandController();

    private final List<PluginSuperCommand> commandsList = new ArrayList<>();

    public void registerCommand(PluginSuperCommand command) {
        commandsList.add(command);
    }

    public Optional<PluginSuperCommand> getCommand(String commandName) {
        return commandsList
                .stream()
                .filter(command ->
                        command.getCommandName().equalsIgnoreCase(commandName)
                                || isArraySubset(command.getCommandAliases(), new String[]{commandName}))
                .findFirst();
    }

    public Optional<PluginSubCommand> getSubCommand(PluginSuperCommand pluginSuperCommand, String[] passedCommandArguments) {
        return pluginSuperCommand.getSubCommands()
                .stream()
                .filter(pluginSubCommand -> isArraySubset(passedCommandArguments, pluginSubCommand.getISubCommand().requiredArguments()))
                .findFirst();
    }

    protected boolean isArraySubset(String[] outer, String[] inner) {
        return new HashSet<>(Arrays.asList(outer)).containsAll(Arrays.asList(inner));
    }
}
