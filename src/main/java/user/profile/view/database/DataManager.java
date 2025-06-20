package user.profile.view.database;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public abstract class DataManager {

    private final Lock lock = new ReentrantLock();

    protected <T> T lock(Supplier<T> supplier) {
        lock.lock();

        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    protected <T> CompletableFuture<T> async(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }
}
