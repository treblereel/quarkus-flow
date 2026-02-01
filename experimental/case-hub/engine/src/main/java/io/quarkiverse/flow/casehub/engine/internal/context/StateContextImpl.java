package io.quarkiverse.flow.casehub.engine.internal.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkiverse.flow.casehub.api.context.StateContext;

public class StateContextImpl implements StateContext {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, Object> data = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public StateContextImpl() {}

    public StateContextImpl(Map<String, Object> initial) {
        if (initial != null) {
            data.putAll(initial);
        }
    }

    @JsonAnyGetter
    @Override
    public Map<String, Object> getData() {
        lock.readLock().lock();
        try {
            return new LinkedHashMap<>(data);
        } finally {
            lock.readLock().unlock();
        }
    }

    @JsonAnySetter
    @Override
    public StateContext set(String key, Object value) {
        lock.writeLock().lock();
        try {
            data.put(key, value);
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Object get(String key) {
        lock.readLock().lock();
        try {
            return data.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public <T> T getAs(String key, Class<T> type) {
        lock.readLock().lock();
        try {
            Object value = data.get(key);
            if (value == null) return null;
            if (type.isInstance(value)) {
                return type.cast(value);
            }
            return mapper.convertValue(value, type);
        } finally {
            lock.readLock().unlock();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrDefault(String key, T defaultValue) {
        lock.readLock().lock();
        try {
            Object value = data.get(key);
            return value != null ? (T) value : defaultValue;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Object computeIfAbsent(String key, Function<String, Object> mappingFunction) {
        lock.writeLock().lock();
        try {
            Object value = data.get(key);
            if (value == null) {
                value = mappingFunction.apply(key);
                if (value != null) {
                    data.put(key, value);
                }
            }
            return value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        lock.writeLock().lock();
        try {
            Object existing = data.get(key);
            if (existing == null) {
                data.put(key, value);
            }
            return existing;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean compareAndSet(String key, Object expected, Object newValue) {
        lock.writeLock().lock();
        try {
            Object current = data.get(key);
            if (Objects.equals(current, expected)) {
                data.put(key, newValue);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public StateContext update(String key, Function<Object, Object> updateFunction) {
        lock.writeLock().lock();
        try {
            Object current = data.get(key);
            Object newValue = updateFunction.apply(current);
            if (newValue != null) {
                data.put(key, newValue);
            } else {
                data.remove(key);
            }
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public String getString(String key) {
        Object v = get(key);
        return v != null ? v.toString() : null;
    }

    @Override
    public Integer getInt(String key) {
        Object v = get(key);
        if (v == null) return null;
        if (v instanceof Number n) return n.intValue();
        return Integer.parseInt(v.toString());
    }

    @Override
    public Long getLong(String key) {
        Object v = get(key);
        if (v == null) return null;
        if (v instanceof Number n) return n.longValue();
        return Long.parseLong(v.toString());
    }

    @Override
    public Double getDouble(String key) {
        Object v = get(key);
        if (v == null) return null;
        if (v instanceof Number n) return n.doubleValue();
        return Double.parseDouble(v.toString());
    }

    @Override
    public Boolean getBoolean(String key) {
        Object v = get(key);
        if (v == null) return null;
        if (v instanceof Boolean b) return b;
        return Boolean.parseBoolean(v.toString());
    }

    @Override
    public <T> List<T> getList(String key, Class<T> elementType) {
        lock.readLock().lock();
        try {
            Object v = data.get(key);
            if (v == null) return null;
            if (v instanceof List<?> list) {
                return list.stream()
                        .map(item -> mapper.convertValue(item, elementType))
                        .toList();
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Object getPath(String path) {
        lock.readLock().lock();
        try {
            return getPathInternal(path);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public String getPathAsString(String path) {
        Object v = getPath(path);
        return v != null ? v.toString() : null;
    }

    @SuppressWarnings("unchecked")
    private Object getPathInternal(String path) {
        String[] parts = path.split("\\.");
        Object current = data;

        for (String part : parts) {
            if (current instanceof Map<?, ?> map) {
                current = map.get(part);
            } else {
                return null;
            }
            if (current == null) return null;
        }
        return current;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StateContext setPath(String path, Object value) {
        lock.writeLock().lock();
        try {
            String[] parts = path.split("\\.");
            Map<String, Object> current = data;

            for (int i = 0; i < parts.length - 1; i++) {
                Object next = current.get(parts[i]);
                if (next == null) {
                    next = new ConcurrentHashMap<String, Object>();
                    current.put(parts[i], next);
                }
                if (next instanceof Map) {
                    current = (Map<String, Object>) next;
                } else {
                    throw new IllegalStateException(
                            "Cannot set path: " + parts[i] + " is not a Map");
                }
            }
            current.put(parts[parts.length - 1], value);
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public StateContext setAll(Map<String, Object> values) {
        if (values == null || values.isEmpty()) return this;
        lock.writeLock().lock();
        try {
            data.putAll(values);
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Map<String, Object> getAll(String... keys) {
        lock.readLock().lock();
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            for (String key : keys) {
                Object value = data.get(key);
                if (value != null) {
                    result.put(key, value);
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean contains(String key) {
        lock.readLock().lock();
        try {
            return data.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public StateContext remove(String key) {
        lock.writeLock().lock();
        try {
            data.remove(key);
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public StateContext clear() {
        lock.writeLock().lock();
        try {
            data.clear();
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @JsonIgnore
    @Override
    public Set<String> getKeys() {
        lock.readLock().lock();
        try {
            return new HashSet<>(data.keySet());
        } finally {
            lock.readLock().unlock();
        }
    }

    @JsonIgnore
    @Override
    public boolean isEmpty() {
        lock.readLock().lock();
        try {
            return data.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    @JsonIgnore
    @Override
    public int size() {
        lock.readLock().lock();
        try {
            return data.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public StateContext merge(StateContext other) {
        if (other == null) return this;
        lock.writeLock().lock();
        try {
            data.putAll(other.getData());
            return this;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public StateContext snapshot() {
        lock.readLock().lock();
        try {
            return new StateContextImpl(deepCopy(data));
        } finally {
            lock.readLock().unlock();
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> deepCopy(Map<String, Object> source) {
        Map<String, Object> copy = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = deepCopy((Map<String, Object>) value);
            } else if (value instanceof List) {
                value = new ArrayList<>((List<?>) value);
            }
            copy.put(entry.getKey(), value);
        }
        return copy;
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            return data.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateContextImpl that)) return false;
        lock.readLock().lock();
        try {
            that.lock.readLock().lock();
            try {
                return data.equals(that.data);
            } finally {
                that.lock.readLock().unlock();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int hashCode() {
        lock.readLock().lock();
        try {
            return data.hashCode();
        } finally {
            lock.readLock().unlock();
        }
    }
}
