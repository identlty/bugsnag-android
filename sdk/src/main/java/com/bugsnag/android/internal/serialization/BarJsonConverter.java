package com.bugsnag.android.internal.serialization;

import com.bugsnag.android.internal.serialization.api.JsonDeserializer;
import com.bugsnag.android.internal.serialization.api.JsonInput;
import com.bugsnag.android.internal.serialization.api.JsonOutput;
import com.bugsnag.android.internal.serialization.api.JsonSerializer;

import java.io.IOException;

/**
 * See {@link FooJsonConverter} for a full example of a serializer
 */
class BarJsonConverter implements JsonSerializer<BarDataClass>, JsonDeserializer<BarDataClass> {
    @Override
    public BarDataClass fromJson(JsonInput input) throws IOException {
        return null;
    }

    @Override
    public void toJson(BarDataClass obj, JsonOutput output) throws IOException {
    }
}

