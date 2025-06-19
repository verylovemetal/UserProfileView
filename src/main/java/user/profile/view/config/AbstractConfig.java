package user.profile.view.config;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import user.profile.view.ProfilePlugin;
import user.profile.view.config.provider.ConfigProviderRecord;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class AbstractConfig {

    private final Map<String, String> placeholder = new ConcurrentHashMap<>();

    private final FileConfiguration configuration;

    public AbstractConfig() {
        configuration = YamlConfiguration.loadConfiguration(getFile(getPath()));
    }

    public ConfigProviderRecord get(String path, UUID playerUUID) {
        return new ConfigProviderRecord(this, path, playerUUID, new HashMap<>());
    }

    @SneakyThrows
    private File getFile(String path) {
        File dataFolder = ProfilePlugin.getInstance().getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdir();

        File file = new File(dataFolder, new File(path).getName());

        if (!file.exists()) {
            InputStream inputStream = ProfilePlugin.getInstance().getResource(path);
            if (inputStream != null) {
                Files.copy(inputStream, file.toPath());
            } else {
                file.createNewFile();
            }
        }

        return file;
    }

    public abstract String getPath();
}
