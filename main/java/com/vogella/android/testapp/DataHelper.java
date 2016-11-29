/**
 * Created by André Ferreira on 22/04/2016.
 */

package com.vogella.android.testapp;

import com.vogella.android.testapp.rest.models.AthleteRest;

import java.io.Serializable;
import java.util.ArrayList;


public class DataHelper implements Serializable {

    public ArrayList<AthleteRest> getData() {
        return data;
    }

    public void setData(ArrayList<AthleteRest> data) {
        this.data = data;
    }

    private ArrayList<AthleteRest> data;



}
