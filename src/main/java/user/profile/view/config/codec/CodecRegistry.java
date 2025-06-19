package user.profile.view.config.codec;

import lombok.Getter;
import user.profile.view.config.codec.impl.IntegerCodec;
import user.profile.view.config.codec.impl.ListCodec;
import user.profile.view.config.codec.impl.StringCodec;

import java.util.*;

public class CodecRegistry {

    @Getter
    private static final CodecRegistry instance = new CodecRegistry();

    private final Map<Class<?>, ICodec<?>> registerCodecs = new HashMap<>();

    public CodecRegistry() {
        registerCodec(new StringCodec());
        registerCodec(new ListCodec());
        registerCodec(new IntegerCodec());
    }

    public void registerCodec(ICodec<?> codec) {
        this.registerCodecs.put(codec.getClassType(), codec);
    }

    @SuppressWarnings("unchecked")
    public <T> ICodec<T> getCodec(Class<?> classType) {
        ICodec<?> direct = registerCodecs.get(classType);
        if (direct != null) return (ICodec<T>) direct;

        for (Map.Entry<Class<?>, ICodec<?>> entry : registerCodecs.entrySet()) {
            if (entry.getKey().isAssignableFrom(classType)) {
                return (ICodec<T>) entry.getValue();
            }
        }

        return null;
    }
}
