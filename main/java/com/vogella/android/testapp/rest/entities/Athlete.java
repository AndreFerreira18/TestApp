package com.vogella.android.testapp.rest.entities;

/**
 * Created by Gio on 15-12-2014.
 */
public interface Athlete {

    String SELF = "athlete";

    String ID = "id";

    String NAME = "name";

    String FULL_NAME = "full_name";

    String GENDER = "gender";

    String COUNTRY = "country";

    String CLUB = "club";

    String CATEGORY = "category";

    String DORSAL_NUMBER = "dorsal_number";

    String RFID = "rfid";

    String STATUS = "status";

    String TSHIRT_SIZE = "tshirt_size";

    String TRANSPORT = "transport";

    String TRANSPORT_BUDDY = "transport_buddy";

    String PASTA_PARTY = "pasta_party";

    String PASTA_PARTY_ADULTS = "pasta_party_adults";

    String PASTA_PARTY_CHILDREN = "pasta_party_children";


    /**
     * @return
     */
    int getDorsalNumber();

    /**
     * @return
     */
    String getFullName();

    /**
     * @return
     */
    String getGender();

    /**
     * @return
     */
    int getId();

    /**
     * @return
     */
    String getCountry();

    /**
     * @return
     */
    String getCategory();

    /**
     * @return
     */
    String getClub();

    /**
     * @return
     */
    Competition getCompetition();

    /**
     * @return
     */
    String getName();

    /**
     * @return
     */
    String getRfid();

    /**
     *
     * @return
     */
    String getStatus();

    /**
     *
     * @return
     */
    String getTshirtSize();

    /**
     *
     * @return
     */
    int getPastaPartyAdults();

    /**
     *
     * @return
     */
    int getPastaPartyChildren();

    /**
     *
     * @return
     */
    int getTransportBuddy();

    /**
     *
     * @return
     */
    boolean isTransport();

    /**
     *
     * @return
     */
    boolean isPastaParty();


}
