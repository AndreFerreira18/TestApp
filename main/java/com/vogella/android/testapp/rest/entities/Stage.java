package com.vogella.android.testapp.rest.entities;

/**
 * Created by Gio on 15-12-2014.
 */
public interface Stage{

    String SELF = "stage";

    String ID = "id";

    String DISTANCE = "distance";

    String ELEVATION_GAIN = "elevation_gain";

    String ELEVATION_LOSS  = "elevation_loss";

    String ORDER = "order";

    /**
     *
     * @return
     */
    int getId();

    Checkpoint getCheckpoint();

    /**
     *
     * @return
     */
    Competition getCompetition();

	/**
	 *
	 * @return
	 */
    int getDistance();

    /**
     *
     * @return
     */
    Time getFirstTime();

    /**
     *
     * @return
     */
    int getElevationGain();

	/**
	 *
	 * @return
	 */
    int getElevationLoss();

    /**
     *
     * @return
     */
    int getOrder();

    /**
     *
     * @return
     */
    Time[] getTimes();

    /**
     *
     * @param time
     * @return
     */
    int getPosition(Time time);
}
