package com.vogella.android.testapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.vogella.android.testapp.rest.Callback;
import com.vogella.android.testapp.rest.Rest;
import com.vogella.android.testapp.rest.models.EventRest;
import com.vogella.android.testapp.utils.AndroidUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class Events extends Activity implements Callback<List<EventRest>> {
    public static ArrayList<String> names;
    //Declaração de variáveis
    ListView listView;
    File pdfFolder2;
    private ArrayList<EventRest> eventos;
    private String password;
    private int id;
    View progressOverlay;
    @Override
    //Método onde se instanciam os componentes gráficos e se definem os sub-métodos do tipo
    //"listener" para os botões. É também estabelecida a primeira ligação à API
    //para ter acesso à lista de eventos
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        listView = (ListView) findViewById(R.id.eventlist);
        progressOverlay = findViewById(R.id.progress_overlay);
        AndroidUtils utils = new AndroidUtils();
        utils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);
        TextView textview = new TextView(Events.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        textview.setText("Lista de Eventos\n (Seleccione o evento desejado)\n\n");
        listView.addHeaderView(textview);

        names = new ArrayList<>();
        eventos = new ArrayList<>();

        pdfFolder2 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "LOGFILE GP MIUT");

        GsonBuilder gson = new GsonBuilder();
        gson.setDateFormat("yyyy-MM-dd HH:mm:ss");

        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.addConverterFactory(GsonConverterFactory.create(gson.create()));

        Rest rest = new Rest(retrofit, (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE));
        rest.setApi("http://apus.uma.pt/~a2061105/v2.2/index.php/api/");
        rest.getEvents("", this);
    }

    @Override
    //Este método é chamado quando é recebi a resposta da API relativamente ao pedido
    //da lista de eventos
    public void onResponse(List<EventRest> lista) {
        for (int i = 0; i < lista.size(); i++) {
            EventRest event = lista.get(i);
            String a = event.getName();
            names.add(i, a);
            eventos.add(i, event);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);

        searchEvent();

    }

    ////Método para efetuar a navegação para o Menu seguinte
    public void avancaMenu() {

//        Intent myIntent = new Intent(this, Validation.class);
        Intent myIntent = new Intent(this, InsertNumber.class);
        startActivity(myIntent);

    }

    @Override
    public void onError(com.vogella.android.testapp.rest.Error error) {

    }

    @Override
    //Guarda informação relevante quando esta activity se fecha.
    protected void onDestroy() {

        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String hora = calendar.getTime().toString().substring(11, 19);
        String info = hora + "  APP Fechou Events  ";
        addInfo(info);
        super.onDestroy();

    }

    //Adiciona informação ao ficheiro LOGFILE
    public void addInfo(String info) {

        try {

            File infos = new File(pdfFolder2 + ".txt");

            Date d = new Date();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            FileWriter fw = new FileWriter(infos, true);

            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(info);

            out.close();
            bw.close();
            fw.close();


        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    //método para seleccionar o evento desejado
    public void searchEvent() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //int itemPosition = position;

                String itemValue = (String) listView.getItemAtPosition(position);


                if (itemValue.equals("Madeira Island Ultra Trail 2016")) {
                    AndroidUtils.animateView(progressOverlay, View.GONE, 0, 200);
                    avancaMenu();
                }
//                if(eventExists(itemValue)){
//                    pedePassword();
//                }
            }
        });
    }

    //Método que verifica a existência de um evento.
    public boolean eventExists(String name) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getName().equals(name)) {
                id = eventos.get(i).getId();
                return true;
            }
        }
        return false;
    }

    //Método para pedir a password do evento seleccionado
    public void pedePassword() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Events.this);
        final EditText edittext = new EditText(Events.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            edittext.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        alert.setMessage("Password:");
        alert.setView(edittext);
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = edittext.getText().toString();
                SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Password", password);
                editor.putInt("Id", id);
                // Commit the edits!
                editor.apply();
                avancaMenu();

            }
        });
        alert.setNegativeButton("Cancelar", null);

        alert.show();

    }
}