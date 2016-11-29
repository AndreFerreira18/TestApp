package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vogella.android.testapp.rest.entities.Athlete;
import com.vogella.android.testapp.rest.entities.Event;

/**
 * Created by Gio on 13-11-2015.
 */
public class EventRest implements Event{

    /**
     *
     */
    @SerializedName(ID)
	@Expose
	private int id;

    /**
     *
     */
    @SerializedName(NAME)
	@Expose
	private String name;

    /**
     *
     */
	@SerializedName(SHORT_NAME)
	@Expose
	private String shortName;

    /**
     *
     */
    @SerializedName(DESCRIPTION)
    @Expose
    private String description;

    /**
     *
     */
     @SerializedName(START)
     @Expose
    private Date start;

    /**
     *
     */
    @SerializedName(END)
    @Expose
    private Date end;

    /**
     *
     */
    @SerializedName(IMAGE)
    @Expose
    private String image;

    /**
     *
     */
    @SerializedName(TOKEN)
    @Expose
    private String token;

    /**
     *
     */
    @SerializedName(URL)
    @Expose
    private String url;

    /**
     *
     */
    @SerializedName(UPDATE_TIMESTAMP)
    @Expose
    private Date updateTimestamp;

    /**
     *
     */
    @SerializedName(IS_PROTECTED)
    @Expose
    private boolean isProtected;

    /**
     *
     */
    @Expose
    private List<CompetitionRest> competitions;

    /**
     *
     */
    @Expose
    private List<CheckpointRest> checkpoints;

    /**
     *
     */
    @Expose
    private List<TimeRest> times;

    /**
     *
     */
    public EventRest(){

        this.isProtected = false;

        this.times = new ArrayList();

        this.checkpoints = new ArrayList();

        this.competitions = new ArrayList();
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object){
        return object instanceof Event && ((Event)object).getId() == this.id;
    }

    /**
     *
     * @return
     */
    @Override
    public CheckpointRest[] getCheckpoints(){
        return this.checkpoints.toArray(new CheckpointRest[0]);
    }

    /**
     * @return
     */
    @Override
    public int getCompetitionsCount() {
        return this.competitions.size();
    }

    /**
     *
     * @param checkpoint
     * @return
     */
//    @Override
//    public String getCheckpointTimesCount(Checkpoint checkpoint) {
//        return "";
//    }

    /**
     *
     * @return
     */
     @Override
	public CompetitionRest[] getCompetitions(){
	    return competitions.toArray(new CompetitionRest[0]);
	}

    /**
     *
     * @return
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @return
     */
    @Override
    public Date getEndDate() {
        return this.end;
    }

    @Override
    public Athlete getAthleteByRfid(String rfid) {
        return null;
    }

    @Override
    public Athlete getAthleteByDorsalNumber(int dorsalNumber) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getAthletesCount() {

        int count = 0;

        for(CompetitionRest competition : this.competitions)
            count += competition.getAthleteCount();

        return count;
    }

    /**
     *
     * @return
     */
    public int getId(){
        return this.id;
    }

    /**
     *
     * @return
     */
    @Override
    public String getImageUrl() {
        return this.image;
    }

    /**
     * @return
     */
    @Override
    public String getToken() {
        return this.token;
    }

    /**
     *
     * @return
     */
    public TimeRest[] getTimes() {
        return this.times.toArray(new TimeRest[0]);
    }

    /**
     *
     * @return
     */
    @Override
    public Date getUpdateTimestamp() {
        return this.updateTimestamp;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isProtected() {
        return this.isProtected;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return
     */
    public String getShortName(){
        return this.shortName;
    }

    /**
     *
     * @return
     */
    @Override
    public Date getStartDate() {
        return this.start;
    }

    /**
     *
     * @return
     */
    @Override
    public Date getEarlyCompetitionStartDate() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getUrl() {
        return this.url;
    }

}
