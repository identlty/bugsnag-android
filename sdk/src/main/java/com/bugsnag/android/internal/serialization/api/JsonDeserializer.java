package com.bugsnag.android.internal.serialization.api;

import java.io.IOException;

/**
 * Implementations are responsible for serializing a JSON input into a JVM object.
 * <p>
 * See {@link JsonSerializer}
 */
public interface JsonDeserializer<T> {
    T fromJson(JsonInput input) throws IOException;
}
