package dgu.camputhon.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TimeCategoryName {
    THIRTY_MINUTES("30min"),
    ONE_HOUR("1hour"),
    TWO_HOURS("2hours"),
    THREE_HOURS("3hours"),
    FOUR_HOURS("4hours");

    private final String value;

    TimeCategoryName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
