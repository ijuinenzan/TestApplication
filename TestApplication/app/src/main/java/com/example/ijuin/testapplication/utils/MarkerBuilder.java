package com.example.ijuin.testapplication.utils;

/**
 * Created by Khang Le on 12/30/2017.
 */

public class MarkerBuilder {

    private double mLatitude, mLongitude;
    private String mColor;
    private Character mLabel;
    private MarkerSize mSize;

    private static final String VALUE_SEPARATOR = "%7C";

    enum MarkerSize {
        NORMAL,
        SMALL,
        MID,
        TINY
    }

    public MarkerBuilder() {
        mLatitude = 0;
        mLongitude = 0;
        mColor = null;
        mLabel = null;
        mSize = MarkerSize.NORMAL;
    }

    public MarkerBuilder position(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;

        return this;
    }

    public String build() {
        StringBuilder result = new StringBuilder();

        if (mColor != null) {
            result.append("color:0x").append(mColor).append(VALUE_SEPARATOR);
        }

        if (mLabel != null) {
            result.append("label:").append(mLabel).append(VALUE_SEPARATOR);
        }

        switch (mSize) {
            case SMALL:
                result.append("size:small").append(VALUE_SEPARATOR);
                break;
            case MID:
                result.append("size:mid").append(VALUE_SEPARATOR);
                break;
            case TINY:
                result.append("size:tiny").append(VALUE_SEPARATOR);
                break;
        }

        result.append(mLatitude).append(",").append(mLongitude);

        return result.toString();
    }
}
