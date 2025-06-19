package user.profile.view.menu.button;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Builder
@Getter
public class MenuButton {

    private final ItemStack itemIcon;
    private final Consumer<Player> onClick;

    public void onClick(Player player) {
        onClick.accept(player);
    }
}
