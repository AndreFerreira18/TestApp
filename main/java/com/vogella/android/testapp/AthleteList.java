package com.vogella.android.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.GsonBuilder;
import com.vogella.android.testapp.rest.Callback;
import com.vogella.android.testapp.rest.Rest;
import com.vogella.android.testapp.rest.models.AthleteRest;
import com.vogella.android.testapp.rest.models.CompetitionRest;
import com.vogella.android.testapp.rest.models.EventRest;

import java.util.ArrayList;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AthleteList extends Activity implements Callback<EventRest> {

    ListView listView;
    public ArrayList<String> names;
    public String event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_list);
        names = new ArrayList<String>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String user = extras.getString("User");
        }
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        String password = settings.getString("Password", "");
        int id = settings.getInt("Id", 0);


        GsonBuilder gson = new GsonBuilder();
        gson.setDateFormat("yyyy-MM-dd HH:mm:ss");

        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.addConverterFactory(GsonConverterFactory.create(gson.create()));

        Rest rest = new Rest(retrofit, (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE));

        rest.setApi("http://apus.uma.pt/~a2061105/v2.2/index.php/api/");

        //rest.getEvent(33, "sidra", this);
        //rest.getEvent(23,"20miut16",this);
        rest.getEvent(id, password, this);

    }


    @Override
    public void onResponse(EventRest data) {
        CompetitionRest[] comp = data.getCompetitions();
        int index = 0;
        int count = 1;
        for (CompetitionRest comp1 : comp) {
            names.add("PROVA: " + comp1.getName());
            AthleteRest[] athletes = comp1.getAthletes();
            for (int i = 0; i < athletes.length; i++) {
                AthleteRest a = athletes[i];
                String b = a.getName();

                names.add(index + i + count, "[" + a.getDorsalNumber() + "] " + b + "\n" +
                        "Nacionalidade: " + a.getCountry() +
                        "\nEscalão: " + a.getCategory() +
                        "\nClube: " + a.getClub() +
                        "\nGénero: " + a.getGender() +
                        "\nEstado: " + a.getStatus() +
                        "\nTamanho T-shirt: " + a.getTshirtSize());

            }
            count++;
            index = index + athletes.length;

        }

        listView = (ListView) findViewById(R.id.athletelist);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        // Assign adapter to ListView
        listView.setAdapter(adapter);


    }

    @Override
    public void onError(com.vogella.android.testapp.rest.Error error) {

    }

}
