package com.vogella.android.testapp.rest.entities;

/**
 * Created by Gio on 2016-02-23.
 */
public interface Place {

    /**
     *
     */
    String SELF = "place";

    /**
     *
     */
    String ID = "id";

    /**
     *
     */
    String NAME = "name";

    /**
     *
     */
    String ALTITUDE = "altitude";

    /**
     *
     */
    String LATITUDE  = "latitude";

    /**
     *
     */
    String LONGITUDE = "longitude";

    /**
     *
     * @return
     */
    int getAltitude();

    /**
     *
     * @return
     */
    int getId();

    /**
     *
     * @return
     */
    float getLatitude();

    /**
     *
     * @return
     */
    float getLongitude();

    /**
     *
     * @return
     */
    String getName();
}
