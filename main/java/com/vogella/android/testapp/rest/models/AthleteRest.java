package com.vogella.android.testapp.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.vogella.android.testapp.rest.entities.Athlete;
import com.vogella.android.testapp.rest.entities.Competition;

import java.io.Serializable;


/**
 * Created by Gio on 2015-12-27.
 */
public class AthleteRest implements Athlete,Serializable{

    /**
     *
     */
    @SerializedName(CATEGORY)
    @Expose
    private String category;

    /**
     *
     */
    @SerializedName(CLUB)
    @Expose
    private String club;

    /**
     *
     */
    @SerializedName(COUNTRY)
    @Expose
    private String country;

    /**
     *
     */
    @SerializedName(ID)
    @Expose
    private int id;

    /**
     *
     */
    @SerializedName(DORSAL_NUMBER)
    @Expose
    private int dorsalNumber;

    /**
     *
     */
    @SerializedName(FULL_NAME)
    @Expose
    private String fullName;

    /**
     *
     */
    @SerializedName(GENDER)
    @Expose
    private char gender;

    /**
     *
     */
    @SerializedName(NAME)
    @Expose
    private String name;

    /**
     *
     */
    @SerializedName(RFID)
    @Expose
    private String rfid;

    /**
     *
     */
    @SerializedName(STATUS)
    @Expose
    private String status;

    /**
     *
     */
    @SerializedName(TSHIRT_SIZE)
    @Expose
    private String tshirtSize;

    /**
     *
     */
    @SerializedName("competition_id")
    @Expose
    private int competitionId;

    /**
     *
     */
    @SerializedName(TRANSPORT)
    @Expose
    private boolean transport;


    /**
     *
     */
    @SerializedName(TRANSPORT_BUDDY)
    @Expose
    private int transportBuddy;


    /**
     *
     */
    @SerializedName(PASTA_PARTY)
    @Expose
    private boolean pastaParty;

    /**
     *
     */
    @SerializedName(PASTA_PARTY_ADULTS)
    @Expose
    private int pastaPartyAdults;

    /**
     *
     */
    @SerializedName(PASTA_PARTY_CHILDREN)
    @Expose
    private int pastaPartyChildren;

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Athlete && ((Athlete)o).getId() == this.id;
    }

    /**
     *
     * @return
     */
    @Override
    public int getDorsalNumber() {
        return this.dorsalNumber;
    }

    /**
     *
     * @return
     */
    @Override
    public String getFullName() {
        return this.fullName;
    }

    /**
     *
     * @return
     */
    @Override
    public String getGender() {
        return this.gender+"";
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
    public String getCountry() {
        return this.country;
    }

    /**
     *
     * @return
     */
    @Override
    public String getCategory() {
        return this.category;
    }

    /**
     *
     * @return
     */
    @Override
    public String getClub() {
        return this.club;
    }

    /**
     *
     * @return
     */
    @Override
    public Competition getCompetition() {
        return null;
    }

    /**
     *
     * @return
     */
    public int getCompetitionId(){
        return this.competitionId;
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
    public String getRfid() {
        return this.rfid;
    }

    /**
     * @return
     */
    @Override
    public String getStatus() {
        return this.status;
    }

    /**
     * @return
     */
    @Override
    public String getTshirtSize() {
        return this.tshirtSize;
    }

    /**
     *
     * @return
     */
    @Override
    public int getPastaPartyAdults(){
        return this.pastaPartyAdults;
    }

    /**
     *
     * @return
     */
    @Override
    public int getPastaPartyChildren(){
        return this.pastaPartyChildren;
    }

    /**
     *
     * @return
     */
    @Override
    public int getTransportBuddy(){
        return this.transportBuddy;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isTransport(){
        return this.transport;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isPastaParty(){
        return this.pastaParty;
    }


}
