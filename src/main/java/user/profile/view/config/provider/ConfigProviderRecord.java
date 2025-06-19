package user.profile.view.config.provider;

import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import user.profile.view.Main;
import user.profile.view.config.AbstractConfig;
import user.profile.view.config.codec.CodecRegistry;
import user.profile.view.config.codec.ICodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ConfigProviderRecord(AbstractConfig abstractConfig, String path, UUID playerUUID, Map<String, String> providedPlaceholders) {

    public ConfigProviderRecord withPlaceholder(String placeholder, Object value) {
        providedPlaceholders.put(placeholder, String.valueOf(value));
        return new ConfigProviderRecord(abstractConfig, path, playerUUID, providedPlaceholders);
    }

    public void sendMessage() {
        Object configValue = get(path);
        if (configValue == null) return;

        Class<?> codecType = configValue.getClass();
        if (codecType == ArrayList.class) {
            codecType = List.class;
        }

        ICodec<?> codec = CodecRegistry.getInstance().getCodec(codecType);
        codec.sendMessage(playerUUID, configValue);
    }

    public List<String> getAsList() {
        return get(path);
    }

    public String getAsString() {
        return get(path);
    }

    public int getAsInt() {
        return get(path);
    }

    @SuppressWarnings("unchecked")
    private <T> T get(String path) {
        Configuration configuration = abstractConfig.getConfiguration();
        Object configValue = configuration.get(path);
        if (configValue == null) return null;

        Class<?> classType = configValue.getClass();
        if (classType == ArrayList.class) {
            classType = List.class;
        }

        return (T) CodecRegistry.getInstance().getCodec(classType).getValue(configValue, this);
    }
}
