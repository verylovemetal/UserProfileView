package user.profile.view.command.type.impl;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import user.profile.view.command.type.ISubCommand;

@Getter
public abstract class PluginSubCommand {

    private final String command;
    private final ISubCommand iSubCommand;

    public PluginSubCommand(String command) {
        this.command = command;
        this.iSubCommand = getClass().getAnnotation(ISubCommand.class);
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
