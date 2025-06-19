package user.profile.view.config.codec.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import user.profile.view.config.codec.ICodec;
import user.profile.view.config.provider.ConfigProviderRecord;
import user.profile.view.util.ChatUtil;

import java.util.UUID;

public class StringCodec implements ICodec<String> {

    @Override
    public void sendMessage(UUID playerUUID, Object value) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        player.sendMessage(ChatUtil.format(String.valueOf(value)));
    }

    @Override
    public String getValue(String value, ConfigProviderRecord providerRecord) {
        return replacePlaceholders(value, providerRecord);
    }

    @Override
    public Class<String> getClassType() {
        return String.class;
    }
}