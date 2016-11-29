package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Gio on 2016-02-13.
 */
public class UpdateTimeRest {

    /**
     *
     */
    @SerializedName("timestamp")
    @Expose
    private Date timestamp;

    /**
     *
     */
    @Expose
    private List<TimeRest> times;

    /**
     *
     * @return
     */
    public Date getTimestamp(){
        return this.timestamp;
    }

    /**
     *
     * @return
     */
    public  TimeRest[] getTimes(){
        return this.times.toArray(new TimeRest[0]);
    }
}
