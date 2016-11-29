package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.vogella.android.testapp.rest.entities.Athlete;
import com.vogella.android.testapp.rest.entities.Checkpoint;
import com.vogella.android.testapp.rest.entities.Event;
import com.vogella.android.testapp.rest.entities.Stage;

/**
 * Created by Gio on 2015-12-26.
 */
public class CheckpointRest implements Checkpoint {

    /**
     *
     */
    @Expose
    private PlaceRest place;

    /**
     *
     */
    @SerializedName("is_protected")
    @Expose
    private boolean isProtected;

    /**
     *
     */
    @Expose
    private String password;

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object){
        return object instanceof Checkpoint && ((Checkpoint)object).getPlace().getId() == this.place.getId();
    }

    @Override
    public Event getEvent() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return 0;
    }

    /**
     *
     * @return
     */
     @Override
    public PlaceRest getPlace(){
        return this.place;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param athlete
     * @return
     */
    @Override
    public Stage getStage(Athlete athlete) {
        return null;
    }

    @Override
    public Stage[] getStages() {
        return new Stage[0];
    }

    @Override
    public int getTimesCount() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public int getAthletesCount() {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isProtected() {
        return this.isProtected;
    }
}
