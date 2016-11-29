package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.vogella.android.testapp.rest.entities.Checkpoint;
import com.vogella.android.testapp.rest.entities.Competition;
import com.vogella.android.testapp.rest.entities.Stage;
import com.vogella.android.testapp.rest.entities.Time;

/**
 * Created by Gio on 2015-12-27.
 */
public class StageRest implements Stage {

    /**
     *
     */
    @SerializedName(ID)
    @Expose
    private int id;

    /**
     *
     */
    @SerializedName(DISTANCE)
    @Expose
    private int distance;

    /**
     *
     */
    @SerializedName(ELEVATION_GAIN)
    @Expose
    private int elevationGain;

    /**
     *
     */
    @SerializedName(ELEVATION_LOSS)
    @Expose
    private int elevationLoss;

    /**
     *
     */
    @SerializedName(ORDER)
    @Expose
    private int order;

    /**
     *

    @Expose
    private List<TimeRest> times;*/

    /**
     *
     */
     @SerializedName("place_id")
     @Expose
    private int placeId;

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
    public Checkpoint getCheckpoint() {
        return null;
    }

    @Override
    public Competition getCompetition() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public int getDistance() {
        return this.distance;
    }

    /**
     * @return
     */
    @Override
    public Time getFirstTime() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public int getElevationGain() {
        return this.elevationGain;
    }

    /**
     *
     * @return
     */
    @Override
    public int getElevationLoss() {
        return this.elevationLoss;
    }

    /**
     *
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }

    /**
     *
     * @return
     */
    @Override
    public TimeRest[] getTimes() {
        return new TimeRest[0];//this.times.toArray(new TimeRest[0]);
    }

    /**
     *
     * @param athlete
     * @return
     */
    @Override
    public int getPosition(Time athlete) {
        return 0;
    }

    /**
     *
     * @return
     */
    public int getPlaceId(){
        return this.placeId;
    }
}
