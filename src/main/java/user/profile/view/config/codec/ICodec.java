package user.profile.view.config.codec;

import org.apache.commons.lang3.StringUtils;
import user.profile.view.config.AbstractConfig;
import user.profile.view.config.provider.ConfigProviderRecord;

import java.util.Map;
import java.util.UUID;

public interface ICodec<T> {

    void sendMessage(UUID playerUUID, Object value);

    T getValue(T value, ConfigProviderRecord providerRecord);

    Class<?> getClassType();

    default String replacePlaceholders(String message, ConfigProviderRecord providerRecord) {
        for (Map.Entry<String, String> placeholder : providerRecord.providedPlaceholders().entrySet()) {
            String key = placeholder.getKey();
            message = StringUtils.replace(message, key, placeholder.getValue());
        }

        return message;
    }
}
