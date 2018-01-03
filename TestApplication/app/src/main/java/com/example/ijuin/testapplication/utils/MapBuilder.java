package com.example.ijuin.testapplication.utils;

/**
 * Created by Khang Le on 12/30/2017.
 */


import android.text.TextUtils;

        import java.util.HashSet;
        import java.util.Set;

/**
 * Created by guilhermesilva on 09/06/14.
 *
 * @author Guilherme Silva
 * @version 1.0
 */
public class MapBuilder {
    private int mWidth, mHeight;
    private double mLatitude, mLongitude;
    private String mKey;
    private int mZoom;
    private final Set<MarkerBuilder> mMarkerBuilderList;


    private static final String GOOGLE_MAPS_URL = "http://maps.google.com/maps/api/staticmap?";
    private static final String VALUE_SEPARATOR = "&";

    public MapBuilder() {
        this.mMarkerBuilderList = new HashSet<>();
    }

    /**
     * Sets the center of the map
     *
     * @param latitude  Latitude.
     * @param longitude Longitude.
     * @return the map builder
     */
    public MapBuilder center(double latitude, double longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;

        return this;
    }

    /**
     * Sets the dimensions of map
     *
     * @param width  Width.
     * @param height Height.
     * @return the map builder
     */
    public MapBuilder dimensions(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;

        return this;
    }

    /**
     * Sets the google maps setKey of the map
     *
     * @param key Key.
     * @return the map builder
     */
    public void setKey(String key) {
        this.mKey = key;

    }

    /**
     * Sets the zoom of the map
     *
     * @param zoom Zoom.
     * @return the map builder
     */
    public MapBuilder zoom(int zoom) {
        this.mZoom = zoom;

        return this;
    }

    /**
     * Adds a marker to the map
     *
     * @param builder Builder.
     * @return the map builder
     */
    public void addMarker(MarkerBuilder builder) {
        mMarkerBuilderList.add(builder);

    }

    /**
     * Generates the string url that can be used to fetch the map
     *
     * @return the url string for the map
     */
    public String build() {
        StringBuilder result = new StringBuilder(GOOGLE_MAPS_URL);
        result.append("center=").append(mLatitude).append(",").append(mLongitude).append(VALUE_SEPARATOR);
        result.append("zoom=").append(mZoom).append(VALUE_SEPARATOR);
        result.append("size=").append(mWidth).append("x").append(mHeight).append(VALUE_SEPARATOR);
        result.append("sensor=false");

        for (MarkerBuilder options : mMarkerBuilderList) {
            result.append(VALUE_SEPARATOR).append("markers=").append(options.build());
        }

        if (!TextUtils.isEmpty(mKey)) {
            result.append(VALUE_SEPARATOR).append("setKey=").append(mKey);
        }

        return result.toString();
    }
}