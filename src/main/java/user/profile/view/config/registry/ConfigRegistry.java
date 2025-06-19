package user.profile.view.config.registry;

import lombok.Getter;
import user.profile.view.ProfilePlugin;
import user.profile.view.config.AbstractConfig;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfigRegistry {

    @Getter
    private static final ConfigRegistry instance = new ConfigRegistry();

    private final Map<Class<? extends AbstractConfig>, AbstractConfig> configs = new HashMap<>();

    public void registerDefaultConfigs() {
        ProfilePlugin.getInstance()
                .getConfigurations()
                .forEach(this::registerConfig);
    }

    public void registerConfig(AbstractConfig config) {
        configs.put(config.getClass(), config);
    }

    public <T extends AbstractConfig> T getConfig(Class<T> configClass) {
        return configClass.cast(configs.get(configClass));
    }
}
