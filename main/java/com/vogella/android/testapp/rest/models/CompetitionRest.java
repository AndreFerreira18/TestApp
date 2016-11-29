package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import com.vogella.android.testapp.rest.entities.Athlete;
import com.vogella.android.testapp.rest.entities.Checkpoint;
import com.vogella.android.testapp.rest.entities.Competition;

/**
 * Created by Gio on 2015-12-26.
 */
public class CompetitionRest implements Competition {

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
    @SerializedName(COLOR)
    @Expose
    private String color;

    /**
     *
     */
     @Expose
    private List<AthleteRest> athletes;

    /**
     *
     */
     @Expose
    private List<StageRest> stages;

    /**
     *
     * @param athlete
     * @return
     */
    @Override
    public boolean containsAthlete(Athlete athlete) {
        return this.athletes.contains(athlete);
    }

    /**
     *
     * @return
     */
    @Override
    public int getAthleteCount() {
        return this.athletes.size();
    }

    /**
     *
     * @return
     */
    @Override
    public AthleteRest[] getAthletes(){
        return this.athletes.toArray(new AthleteRest[0]);

    }

    /**
     *
     * @return
     */
    @Override
    public String getColor() {
        return this.color;
    }

    /**
     *
     * @return
     */
    @Override
    public Date getEndDate() {
        return this.end;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return
     */
    @Override
    public String getShortName() {
        return this.shortName;
    }

    /**
     *
     * @param checkpoint
     * @return
     */
    @Override
    public StageRest getStage(Checkpoint checkpoint) {

        for(StageRest stage : this.stages){
            if(!checkpoint.equals(stage.getCheckpoint()))
                continue;

            return stage;
        }
        return null;

    }

    /**
     *
     * @return
     */
    @Override
    public StageRest[] getStages() {
        return this.stages.toArray(new StageRest[0]);
    }

    /**
     *
     * @return
     */
    @Override
    public Date getStartDate() {
        return this.start;
    }
}
