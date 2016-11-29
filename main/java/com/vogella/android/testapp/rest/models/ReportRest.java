package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Gio on 2016-03-29.
 */
public class ReportRest {

    /**
     *
     */
    @SerializedName("save_timestamp")
    @Expose
    private Date saveTimestamp;

    /**
     *
     * @return
     */
    public Date getSaveTimestamp(){
        return this.saveTimestamp;
    }
}
