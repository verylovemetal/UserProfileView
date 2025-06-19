package user.profile.view.database;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import user.profile.view.Main;
import user.profile.view.database.cache.CacheDataManager;
import user.profile.view.database.data.ProfileData;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDataManager extends CacheDataManager {

    public ProfileData loadData(@Nullable UUID key, String playerName) {
        if (key != null && isPresent(key)) {
            ProfileData data = getData(key);
            cache.put(key, data);
            return data;
        }

        ProfileData data = createDefaultData(key, playerName);
        saveData(data.getPlayerUUID(), data);
        return data;
    }

    public ProfileData updateData(UUID key, Consumer<ProfileData> consumer) {
        return lock(() -> {
            ProfileData data = getData(key);
            consumer.accept(data);
            saveData(key, data);
            cache.put(key, data);
            return data;
        });
    }

    public void saveData(UUID key, ProfileData data) {
        DataStorage.getInstance().save(Main.getInstance().getCollectionName(), key, data);
        cache.put(key, data);
    }

    public @Nullable ProfileData getCachedData(UUID key) {
        return cache.get(key);
    }

    public ProfileData getData(UUID key) {
        DataStorage storage = DataStorage.getInstance();
        return storage.loadData(Main.getInstance().getCollectionName(), key);
    }

    public ProfileData getDataByName(String name) {
        DataStorage storage = DataStorage.getInstance();
        return storage.getDataByName(Main.getInstance().getCollectionName(), name);
    }

    public boolean isPresent(UUID key) {
        if (hasCachedData(key)) {
            return true;
        }

        DataStorage storage = DataStorage.getInstance();
        return storage.isPresent(Main.getInstance().getCollectionName(), key);
    }

    private ProfileData createDefaultData(UUID key, String playerName) {
        return getDefaultDataFactory(playerName).apply(key);
    }

    public Function<UUID, ProfileData> getDefaultDataFactory(String playerName) {
        return uuid -> new ProfileData(uuid, playerName, "", "", true);
    }
}