package user.profile.view.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class ChatUtil {

    private final Cache<String, Component> cachedMessages = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public Component format(String message) {
        return cachedMessages.getIfPresent(message) != null
                ? cachedMessages.getIfPresent(message)
                : cacheAndReturn(message);
    }

    private Component cacheAndReturn(String message) {
        Component component = MiniMessage.miniMessage().deserialize(message).decoration(TextDecoration.ITALIC, false);
        cachedMessages.put(message, component);
        return component;
    }
}
