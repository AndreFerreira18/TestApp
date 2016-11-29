package com.vogella.android.testapp.rest.entities;

import java.util.Date;

/**
 * Created by Gio on 2015-12-21.
 */
public interface Event {

    /**
     *
     */
    String SELF = "event";

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
    String SHORT_NAME = "short_name";

    /**
     *
     */
    String DESCRIPTION = "description";

    /**
     *
     */
    String START = "start";

    /**
     *
     */
    String END = "end";

    /**
     *
     */
    String TOKEN = "token";

    /**
     *
     */
    String URL = "url";

    /**
     *
     */
    String IMAGE = "image";

    /**
     *
     */
    String UPDATE_TIMESTAMP = "update_timestamp";

    /**
     *
     */
    String IS_PROTECTED = "is_protected";

    /**
     *
     */
    String PASSWORD = "password";

    /**
     *
     * @param rfid
     * @return
     */
    Athlete getAthleteByRfid(String rfid);

    /**
     *
     * @param dorsalNumber
     * @return
     */
    Athlete getAthleteByDorsalNumber(int dorsalNumber);

    /**
     *
     * @return
     */
    int getAthletesCount();

    /**
     * @return
     */
    int getId();

    /**
     * @return
     */
    Checkpoint[] getCheckpoints();

    /**
     *
     * @return
     */
    int getCompetitionsCount();

    /**
     * @return
     */
    Competition[] getCompetitions();

    /**
     * @return
     */
    String getDescription();

    /**
     * @return
     */
    String getName();

    /**
     * @return
     */
    String getShortName();

    /**
     * @return
     */
    Date getStartDate();

    /**
     *
     * @return
     */
    Date getEarlyCompetitionStartDate();

    /**
     * @return
     */
    Date getEndDate();


    /**
     * @return
     */
    String getImageUrl();

    /**
     *
     * @return
     */
    String getToken();

    /**
     *
     */
    Date getUpdateTimestamp();

    /**
     * @return
     */
    String getUrl();

    /**
     *
     * @return
     */
    boolean isProtected();
}
