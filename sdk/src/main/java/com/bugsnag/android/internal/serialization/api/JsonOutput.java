package com.bugsnag.android.internal.serialization.api;

import android.util.JsonWriter;

import java.io.IOException;

/**
 * This class writes JVM objects to a JSON output.
 *
 * It encapsulates details of the JsonWriter class so that we can provide a more concise API.
 */
public final class JsonOutput {

    public <T> JsonOutput nameValuePair(String key, T obj, JsonSerializer<T> jsonSerializer) throws IOException {
        jsonWriter.name(key);
        jsonSerializer.toJson(obj, this);
        return this;
    }

    public JsonOutput beginObject() throws IOException {
        jsonWriter.beginObject();
        return this;
    }

    public JsonOutput endObject() throws IOException {
        jsonWriter.endObject();
        return this;
    }

    public JsonOutput nameValuePair(String key, String obj) throws IOException {
        jsonWriter.name(key);
        jsonWriter.value(obj);
        return this;
    }

    public interface ObjectOutput {
        void onObject(JsonOutput output);
    }

    // it's worth noting that beginObject and endObject could be combined into one call
    // by passing a lambda that is invoked inbetween.
    // this would guarantee that a JSON object/array is always closed after it is opened
    // in a method (a source of bugs in the past). The example doesn't use this method as it only
    // works well in Kotlin.
    public JsonOutput jsonObject(ObjectOutput objectOutput) throws  IOException {
        jsonWriter.beginObject();
        objectOutput.onObject(this);
        jsonWriter.endObject();
        return this;
    }

    private final JsonWriter jsonWriter = new JsonWriter(null);

}
