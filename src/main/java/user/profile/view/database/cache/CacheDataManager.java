package user.profile.view.database.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import user.profile.view.database.DataManager;
import user.profile.view.database.data.ProfileData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public abstract class CacheDataManager extends DataManager {

    protected final Map<UUID, ProfileData> cache = new ConcurrentHashMap<>();

    public ProfileData getCachedData(UUID playerUUID) {
        return cache.get(playerUUID);
    }

    public void invalidateCache(UUID playerUUID) {
        cache.remove(playerUUID);
    }

    protected void updateCachedData(UUID playerUUID, ProfileData data) {
        cache.put(playerUUID, data);
    }

    protected boolean hasCachedData(UUID playerUUID) {
        return cache.containsKey(playerUUID);
    }
}
