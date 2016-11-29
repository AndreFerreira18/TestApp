package com.vogella.android.testapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;

public class Validation extends Activity {
    //Declaração de variáveis
    ListView listView;
    private String selection;
    File pdfFolder2;

    @Override
    //Método onde se instanciam os componentes gráficos e se definem os sub-métodos do tipo
    //"listener" para os botões. É também definido qual o menu de validações a apresentar
    //(consoante o tipo de utilizador)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.validationlist);
        TextView textview = new TextView(Validation.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }


        textview.setText("Lista de Validações\n (Selecccione a validação pretendida)\n\n");
        listView.addHeaderView(textview);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);

        pdfFolder2 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "LOGFILE GP MIUT");

        String user = settings.getString("Utilizador", "");
        // Defined Array values to show in ListView
        String[] values1 = new String[]{"Autocarro",
                "Kit de Finisher"
        };

        String[] values2 = new String[]{"Autocarro"};

        String[] values3 = new String[]{"Kit de Finisher"};

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        if (user.equals("Master")) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values1);
            listView.setAdapter(adapter);
        } else {
            if (user.equals("Auto1") || user.equals("Auto2") || user.equals("Auto3") || user.equals("Auto4") || user.equals("Auto5") ||
                    user.equals("Auto6") || user.equals("Auto7") || user.equals("Auto8") || user.equals("Auto9") || user.equals("Auto10") ||
                    user.equals("Auto11") || user.equals("Auto12") || user.equals("Auto13") || user.equals("Auto14") || user.equals("Auto15") ||
                    user.equals("Auto16") || user.equals("Auto17") || user.equals("Auto18") || user.equals("Auto19") || user.equals("Auto20")) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values2);
                listView.setAdapter(adapter);
            } else {
                if (user.equals("Kit1") || user.equals("Kit2") || user.equals("Kit3") || user.equals("Kit4") || user.equals("Kit5") ||
                        user.equals("Kit6") || user.equals("Kit7") || user.equals("Kit8") || user.equals("Kit9") || user.equals("Kit10") ||
                        user.equals("Kit11") || user.equals("Kit12") || user.equals("Kit13") || user.equals("Kit14") || user.equals("Kit15") ||
                        user.equals("Kit16") || user.equals("Kit17") || user.equals("Kit18") || user.equals("Kit19") || user.equals("Kit20")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, values3);
                    listView.setAdapter(adapter);
                }
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                String itemValue = (String) listView.getItemAtPosition(position);

                if (itemValue.equals("Autocarro")) {
                    selection = "bus";
                    avancaMenu();
                } else {
                    if (itemValue.equals("Kit de Finisher")) {
                        selection = "kit";
                        avancaMenu();
                    }
                }
            }

        });
    }

    @Override
    //Guarda informação relevante quando esta activity pára.
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Seleccao", selection);

        editor.commit();
    }

    //Método para efetuar a navegação para o Menu seguinte
    public void avancaMenu() {

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Seleccao", selection);
        editor.commit();
        Intent myIntent = new Intent(this, InsertNumber.class);
        startActivity(myIntent);
    }

    @Override
    //Guarda informação relevante quando esta activity se fecha.
    protected void onDestroy() {

        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String hora = calendar.getTime().toString().substring(11, 19);
        String info = hora + "  APP Fechou Validações  ";
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
}
