package user.profile.view.config.codec.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import user.profile.view.config.codec.ICodec;
import user.profile.view.config.provider.ConfigProviderRecord;
import user.profile.view.util.ChatUtil;

import java.util.List;
import java.util.UUID;

public class ListCodec implements ICodec<List<String>> {

    @Override
    @SuppressWarnings("unchecked")
    public void sendMessage(UUID playerUUID, Object value) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        List<String> list = (List<String>) value;
        for (String line : list) {
            player.sendMessage(ChatUtil.format(line));
        }
    }

    @Override
    public List<String> getValue(List<String> value, ConfigProviderRecord providerRecord) {
        return value.stream()
                .map(line -> replacePlaceholders(line, providerRecord))
                .toList();
    }

    @Override
    public Class<?> getClassType() {
        return List.class;
    }
}
