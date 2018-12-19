package com.bigb.sinope;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Writer of JSON with for the support fields in {@link JsonFields}.
 * 
 * @author Francis Beaule
 *
 */
public class JsonObjectWriter {
    /**
     * The internal builder.
     */
    private final JsonObjectBuilder json = Json.createObjectBuilder();

    /**
     * @return The completed {@link JsonObject}
     */
    public JsonObject complete() {
        return this.json.build();
    }

    /**
     * Adds a new property.
     * 
     * @param field The field.
     * @param value The value.
     */
    public void add(JsonFields field, String value) {
        this.json.add(field.getField(), value);
    }

    /**
     * Adds a new value.
     * 
     * @param field The field.
     * @param value The value.
     */
    public void add(JsonFields field, boolean value) {
        this.json.add(field.getField(), value);
    }

    /**
     * Adds a new value.
     * 
     * @param field The field.
     * @param value The value.
     */
    public void add(JsonFields field, long value) {
        this.json.add(field.getField(), value);
    }

    /**
     * Adds a new value.
     * 
     * @param field The field.
     * @param value The value.
     */
    public void add(JsonFields field, BigDecimal value) {
        this.json.add(field.getField(), value);
    }
}
