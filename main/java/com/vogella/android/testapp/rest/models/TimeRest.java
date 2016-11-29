package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import com.vogella.android.testapp.rest.entities.Athlete;
import com.vogella.android.testapp.rest.entities.Stage;
import com.vogella.android.testapp.rest.entities.Time;

/**
 * Created by Gio on 2016-01-17.
 */
public class TimeRest implements Time {

    /**
     *
     */
    @SerializedName("athlete_id")
    @Expose
    private int athleteId;

    /**
     *
     */
    @SerializedName(TIMESTAMP)
    @Expose
    private Date timestamp;

    /**
     *
     */
    @SerializedName("stage_id")
    @Expose
    private int stageId;

    /**
     *
     * @return
     */
    @Override
    public Athlete getAthlete() {
        return null;
    }


    /**
     *
     * @return
     */
    public int getAthleteId() {
        return this.athleteId;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDiffToFirst() {
        return null;
    }

    /**
     *
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
    public int getPosition() {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public Date getTimestamp() {
        return this.timestamp;
    }

    /**
     *
     * @return
     */
    @Override
    public Stage getStage() {
        return null;
    }

    /**
     *
     * @return
     */
    public int getStageId() {
        return this.stageId;
    }

    /**
     *
     * @return
     */
    @Override
    public Status getStatus() {
        return Status.CONFIRMED;
    }

    /**
     *
     * @return
     */
    @Override
    public String getTime() {
        return "";
    }
}
