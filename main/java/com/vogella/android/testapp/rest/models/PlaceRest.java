package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;

import com.vogella.android.testapp.rest.entities.Place;

/**
 * Created by Gio on 2016-02-23.
 */
public class PlaceRest implements Place {

    /**
     *
     */
    @Expose
    private int id;

    /**
     *
     */
    @Expose
    private int altitude;

    /**
     *
     */
    @Expose
    private float latitude;

    /**
     *
     */
    @Expose
    private float longitude;

    /**
     *
     */
    @Expose
    private String name;

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object){
        return object instanceof Place && ((Place)object).getId() == this.getId();
    }

    /**
     *
     * @return
     */
    @Override
    public int getAltitude() {
        return this.altitude;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     *
     * @return
     */
    @Override
    public float getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @return
     */
    @Override
    public float getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }
}
