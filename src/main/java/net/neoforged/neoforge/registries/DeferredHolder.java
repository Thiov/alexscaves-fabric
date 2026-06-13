package net.neoforged.neoforge.registries;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DeferredHolder<R, T extends R> implements Supplier<T>, Holder<T> {

    private final ResourceKey<? extends Registry<R>> registryKey;
    private final Identifier id;
    private final Supplier<? extends T> factory;
    private T value;
    private Holder.Reference<T> holder;

    DeferredHolder(ResourceKey<? extends Registry<R>> registryKey, Identifier id, Supplier<? extends T> factory) {
        this.registryKey = registryKey;
        this.id = id;
        this.factory = factory;
    }

    
    public T get() {
        return resolve();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public synchronized T resolve() {
        if (value != null) {
            return value;
        }

        Registry<T> registry = getRegistry();
        if (registry != null && registry.containsKey(getId())) {
            value = registry.getValue(getId());
            holder = com.github.alexmodguy.alexscaves.server.misc.RegistryCompat.getHolder(registry, id).orElse(null);
            if (value != null) {
                return value;
            }
        }

        // 26.1: publish this registration's id so the BlockBehaviour/Item constructor mixins can
        // stamp it onto the Properties before the engine bakes the loot table / id.
        com.github.alexmodguy.alexscaves.fabric.RegistrationIdContext.push(registryKey, getId());
        try {
            value = factory.get();
        } finally {
            com.github.alexmodguy.alexscaves.fabric.RegistrationIdContext.pop(registryKey);
        }
        if (registry != null) {
            if (registry.containsKey(getId())) {
                holder = com.github.alexmodguy.alexscaves.server.misc.RegistryCompat.getHolder(registry, id).orElse(null);
            } else {
                holder = Registry.registerForHolder(registry, getId(), value);
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private Registry<T> getRegistry() {
        return (Registry<T>) BuiltInRegistries.REGISTRY.getValue(registryKey.identifier());
    }

    private Holder<T> getRegisteredHolder() {
        if (holder != null) {
            return holder;
        }
        Registry<T> registry = getRegistry();
        if (registry == null) {
            return null;
        }
        holder = com.github.alexmodguy.alexscaves.server.misc.RegistryCompat.getHolder(registry, id).orElse(null);
        return holder;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResourceKey<T> getId() {
        return ResourceKey.create((ResourceKey) registryKey, id);
    }

    public ResourceKey<T> getKey() {
        return getId();
    }

    
    public T value() {
        return resolve();
    }

    
    public boolean isBound() {
        return getRegisteredHolder() != null || value != null;
    }

    
    public boolean areComponentsBound() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null && registeredHolder.areComponentsBound();
    }

    
    public DataComponentMap components() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.components() : DataComponentMap.EMPTY;
    }

    
    public boolean is(Identifier location) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(location) : id.equals(location);
    }

    
    public boolean is(ResourceKey<T> resourceKey) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(resourceKey) : getId().equals(resourceKey);
    }

    
    public boolean is(Predicate<ResourceKey<T>> predicate) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(predicate) : predicate.test(getId());
    }

    
    public boolean is(TagKey<T> tagKey) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null && registeredHolder.is(tagKey);
    }

    
    public boolean is(Holder<T> holder) {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.is(holder) : holder == this || holder.value() == value();
    }

    
    public Stream<TagKey<T>> tags() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.tags() : Stream.empty();
    }

    
    public Either<ResourceKey<T>, T> unwrap() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.unwrap() : Either.left(getId());
    }

    
    public Optional<ResourceKey<T>> unwrapKey() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.unwrapKey() : Optional.of(getId());
    }

    
    public Kind kind() {
        Holder<T> registeredHolder = getRegisteredHolder();
        return registeredHolder != null ? registeredHolder.kind() : Kind.REFERENCE;
    }

    
    public boolean canSerializeIn(HolderOwner<T> owner) {
        Holder<T> registeredHolder = getRegisteredHolder();
        if (registeredHolder != null && registeredHolder.canSerializeIn(owner)) {
            return true;
        }
        Registry<T> registry = getRegistry();
        return registry != null && registry.containsKey(getId());
    }
}
