package com.bugsnag.android.internal.serialization.api;

import android.util.JsonReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reads JSON input and converts it to a JVM object
 * <p>
 * It encapsulates details of the JsonReader class so that we can provide a more concise API.
 */
public final class JsonInput {

    /**
     * Attempts to serialize an object value from the JSON. A JsonDeserializer that is capable
     * of serializing the class type must be passed as a parameter.
     */
    public <T> T nextObject(JsonDeserializer<T> deserializer) throws IOException {
        return deserializer.fromJson(this);
    }

    /**
     * Attempts to serialize a string value from the JSON.
     */
    public String nextString() throws IOException {
        return jsonReader.nextString();
    }

    /**
     * Reads all the values in a JSON object and returns them as a Map.
     * <p>
     * The {@link JsonObjectListener} is invoked for each key in the object, and used to build the
     * map. The returned map can then be built into the required object.
     * <p>
     * This is essentially what the previous implementation does, but with individual variables
     * rather than a map.
     */
    public Map<String, Object> readJsonObject(JsonObjectListener fieldListener) throws IOException {
        jsonReader.beginObject();
        Map<String, Object> tempMap = new HashMap<>();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            Object value = fieldListener.onJsonObjectName(key);

            if (value != null) {
                tempMap.put(key, value);
            }
        }
        jsonReader.endObject();
        return tempMap;
    }

    private final JsonReader jsonReader = new JsonReader(null);

    public interface JsonObjectListener<T> {

        /**
         * Invoked whenever a JSON key is encountered in an object. The caller should react to
         * this message by attempting to serialise a value of the correct type.
         */
        T onJsonObjectName(String name) throws IOException;
    }
}
