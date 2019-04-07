package com.bugsnag.android.internal.serialization;

import com.bugsnag.android.internal.serialization.api.JsonDeserializer;
import com.bugsnag.android.internal.serialization.api.JsonInput;
import com.bugsnag.android.internal.serialization.api.JsonOutput;
import com.bugsnag.android.internal.serialization.api.JsonSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * This class serializes and deserializes {@link FooDataClass} objects to/from JSON. In this
 * example, it serialises a String property, and a BarDataClass property, which by convention
 * has its own converter classes.
 * <p>
 * It implements a {@link JsonSerializer} which will be invoked when a JVM object is being written
 * to an OutputStream.
 * <p>
 * It also implements a {@link JsonDeserializer} which will be invoked when a JSON input is being
 * read into a JVM object.
 * <p>
 * By convention, non-primitive types should implement separate (de)serializers. These should then
 * be invoked using composition e.g. see {@link #barJsonConverter}
 */
class FooJsonConverter implements JsonSerializer<FooDataClass>, JsonDeserializer<FooDataClass> {

    @Override
    public void toJson(FooDataClass obj, JsonOutput output) throws IOException {
        output.beginObject() // creates a new JSON object
            .nameValuePair("str", obj.str) // serialise a primitive value only
            .nameValuePair("bar", obj.bar, barJsonConverter); // each object requires a serializer
        output.endObject();
    }

    @Override
    public FooDataClass fromJson(final JsonInput input) throws IOException {
        Map<String, Object> jsonObject = input.readJsonObject(new JsonInput.JsonObjectListener() {
            @Override
            public Object onJsonObjectName(String name) throws IOException {
                switch (name) {
                    case "str":
                        return input.nextString();
                    case "bar":
                        return input.nextObject(barJsonConverter);
                    default:
                        return null;
                }
            }
        });
        return new FooDataClass(
            (String) jsonObject.get("str"),
            (BarDataClass) jsonObject.get("bar")
        );
    }

    // Handles the serialization of the BarDataClass type.
    // We should aim for a convention that each type should have its own serializer. One of the
    // problems with the previous implementation was that methods tended to gain too much
    // responsibility in classes with multiple complex fields.
    private final BarJsonConverter barJsonConverter = new BarJsonConverter();
}
