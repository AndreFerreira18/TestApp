package com.vogella.android.testapp.rest.entities;

import java.util.Date;


/**
 * Created by Gio on 15-12-2014.
 */
public interface Competition {

    /**
     *
     */
    String SELF = "competition";

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
    String COLOR = "color";

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
     * @param athlete
     * @return
     */
    boolean containsAthlete(Athlete athlete);

    /**
     *
     * @return
     */
    int getAthleteCount();

    /**
     *
     * @return
     */
	Athlete[] getAthletes();

    /**
     *
     * @return
     */
    String getColor();

    /**
     *
     * @return
     */
    Date getEndDate();

    /**
     *
     * @return
     */
    int getId();

    /**
     *
     * @return
     */
    String getName();

	/**
	 *
	 * @return
	 */
	String getShortName();

    /**
     *
     * @param checkpoint
     * @return
     */
	Stage getStage(Checkpoint checkpoint);

    /**
     *
     * @return
     */
    Stage[] getStages();

	/**
	 *
	 * @return
	 */
	Date getStartDate();
}
