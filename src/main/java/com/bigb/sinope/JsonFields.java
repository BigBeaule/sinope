package com.bigb.sinope;

/**
 * The fields that will be present in a resulting JSON object.
 * 
 * @author Francis Beaule
 *
 */
public enum JsonFields {
    /** Answers data value fields **/
    OUTDOOR_TEMP("outdoorTemperature"), //
    ROOM_TEMP("roomTemperature"), //
    SET_POINT("setPoint"), //
    HEAT_LVL("heatLevel"), //
    LOAD("load"), //
    TYPE("type"), //
    AWAY("isAway"), //

    /** Properties **/
    SEQUENCE("sequence"), //
    DEV_ID("deviceId"), //
    STATUS("status"), //
    MORE("hasMore"), //
    KEY("key"), //
    CMD("command"), //
    TIMEOUT("timeout"), //
    VERSION_MAJOR("version.major"), //
    VERSION_MINOR("version.minor"), //
    VERSION_UPDATE("version.update"); //

    /**
     * The field name.
     */
    private final String field;

    /**
     * @param field The field name.
     */
    private JsonFields(String field) {
        this.field = field;
    }

    /**
     * @return The field name.
     */
    public String getField() {
        return field;
    }
}
