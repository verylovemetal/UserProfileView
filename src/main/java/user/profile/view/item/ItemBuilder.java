package user.profile.view.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.profile.view.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    public ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.displayName(ChatUtil.format(displayName));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<Component> finalLore = new ArrayList<>();
        lore.forEach(string -> finalLore.add(ChatUtil.format(string)));
        itemMeta.lore(finalLore);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}