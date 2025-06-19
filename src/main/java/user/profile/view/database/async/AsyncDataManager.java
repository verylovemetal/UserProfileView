package user.profile.view.database.async;

import lombok.Getter;
import user.profile.view.database.AbstractDataManager;
import user.profile.view.database.data.ProfileData;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AsyncDataManager extends AbstractDataManager {

    @Getter
    private static final AsyncDataManager instance = new AsyncDataManager();

    public CompletableFuture<ProfileData> updateDataAsync(UUID key, Consumer<ProfileData> consumer) {
        return async(() -> updateData(key, consumer));
    }

    public CompletableFuture<ProfileData> loadDataAsync(UUID key, String playerName) {
        return async(() -> loadData(key, playerName));
    }

    public CompletableFuture<Void> saveDataAsync(UUID key, ProfileData profileData) {
        return async(() -> saveData(key, profileData));
    }

    public CompletableFuture<ProfileData> getDataAsync(UUID key) {
        return async(() -> getData(key));
    }

    public CompletableFuture<ProfileData> getDataByNameAsync(String playerName) {
        return async(() -> getDataByName(playerName));
    }

    public CompletableFuture<Boolean> isPresentAsync(UUID key) {
        return async(() -> isPresent(key));
    }
}
