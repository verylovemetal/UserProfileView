package user.profile.view.config.codec.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import user.profile.view.config.codec.ICodec;
import user.profile.view.config.provider.ConfigProviderRecord;

import java.util.UUID;

public class IntegerCodec implements ICodec<Integer> {

    @Override
    public void sendMessage(UUID playerUUID, Object value) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        player.sendMessage(String.valueOf(value));
    }

    @Override
    public Integer getValue(Integer value, ConfigProviderRecord providerRecord) {
        return value;
    }

    @Override
    public Class<?> getClassType() {
        return Integer.class;
    }
}
