package com.vogella.android.testapp.rest.entities;

/**
 * Created by Gio on 15-12-2014.
 */
public interface Checkpoint {

    /**
     *
     */
    String SELF = "checkpoint";

    /**
     *
     */
    String ID = "id";

    /**
     *
     */
    String PASSWORD = "password";

    /**
     *
     * @return
     */
    Event getEvent();

    /**
     *
     * @return
     */
    int getId();

    /**
     *
     * @return
     */
    Place getPlace();

    /**
     *
     * @return
     */
    String getPassword();

    /**
     *
     * @param athlete
     * @return
     */
    Stage getStage(Athlete athlete);

    /**
     *
     * @return
     */
    Stage[] getStages();


    /**
     *
     * @return
     */
    int getTimesCount();

    /**
     *
     * @return
     */
    int getAthletesCount();

    /**
     *
     * @return
     */
    boolean isProtected();
}
