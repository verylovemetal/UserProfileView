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

    private ItemStack itemStack;
    public ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setMaterial(Material material) {
        itemStack = new ItemStack(material);
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemMeta.setMaxStackSize(64);
        itemStack.setAmount(amount);
        return this;
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

    public ItemBuilder setLoreComponent(List<Component> lore) {
        itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<Component> finalLore = new ArrayList<>();
        for (String string : lore) finalLore.add(ChatUtil.format(string));
        itemMeta.lore(finalLore);
        return this;
    }

    public ItemBuilder setGlow(boolean glow) {
        if (!glow) return this;
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}