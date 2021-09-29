package com.metagxd.solvotest.model;

/**
 * This enum representation of user action in {@link com.metagxd.solvotest.Main}
 */
public enum Action {
    EXIT(0),
    CREATE_LOADS(1),
    GET_INFO(2),
    EXPORT(3),
    NONE(4);

    private final int value;

    Action(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
