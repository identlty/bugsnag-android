package com.bugsnag.android.internal.serialization.api;

import java.io.IOException;

/**
 * Implementations are responsible for serializing a JVM object into JSON, from a given object.
 * <p>
 * It is intended that each class would implement its own JsonSerializer, to reduce complexity. For
 * example, if a root class had a non-primitive field, it should define a separate JsonSerializer
 * for that non-primitive type, and createa composite JsonSerializer for the root class. This would
 * avoid the pitfall of single methods/classes having too much responsibility, which was a problem
 * in some areas of the previous implementation
 */
public interface JsonSerializer<T> {
    void toJson(T obj, JsonOutput output) throws IOException;
}
