package user.profile.view.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.profile.view.Main;

public abstract class AbstractCommand extends Command {

    protected final Main plugin;

    protected AbstractCommand(String commandName, Main plugin) {
        super(commandName);
        this.plugin = plugin;
    }

    public void attemptRunCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        onCommand(sender, args);
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
