package com.vogella.android.testapp.rest.models;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Gio on 2016-04-16.
 */
public class AthleteCheckRest {

    /**
     *
     */
    @SerializedName("athlete_id")
    private int athleteId;

    /**
     *
     */
    @SerializedName("term")
    private String term;

    /**
     *
     */
    @SerializedName("checked")
    private boolean checked;

    /**
     *
     */
    @SerializedName("date")
    private Date date;

    /**
     *
     * @return
     */
    public int getAthleteId(){
        return this.athleteId;
    }

    /**
     *
     * @return
     */
    public String getTerm(){
        return this.term;
    }

    /**
     *
     * @return
     */
    public boolean isChecked(){
        return this.checked;
    }

    /**
     *
     * @return
     */
    public Date getDate(){
        return this.date;
    }
}