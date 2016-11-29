package com.vogella.android.testapp.rest.entities;

import java.util.Date;


/**
 * Created by Gio on 2016-01-17.
 */
public interface Time {

    /**
     *
     */
    String SELF = "time";

    /**
     *
     */
    String TIMESTAMP = "timestamp";

    /**
     *
     */
    String STATUS = "status";

    /**
     *
     */
    String ID = "id";


    /**
     *
     * @return
     */
    int getId();

    /**
     *
     * @return
     */
    String getDiffToFirst();

    /**
     *
     * @return
     */
    Athlete getAthlete();

    /**
     *
     * @return
     */
    int getPosition();

    /**
     *
     * @return
     */
    Stage getStage();


    /**
     *
     * @return
     */
    String getTime();

    /**
     *
     * @return
     */
    Date getTimestamp();



    /**
     *
     * @return
     */
    Status getStatus();

    /**
     *
     */
    enum Status{
        PENDING,
        SENT,
        DELIVERED,
        CONFIRMED,
        FAILED
    }

}
