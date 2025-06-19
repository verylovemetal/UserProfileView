package user.profile.view.config.provider;

import lombok.Getter;
import user.profile.view.config.AbstractConfig;
import user.profile.view.config.registry.ConfigRegistry;

@Getter
public class ConfigProvider {

    public static <T extends AbstractConfig> T getConfig(Class<T> configClass) {
        return ConfigRegistry.getInstance().getConfig(configClass);
    }
}