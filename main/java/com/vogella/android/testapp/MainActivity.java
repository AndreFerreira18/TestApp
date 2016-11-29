package com.vogella.android.testapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    Button confirmButton;
    EditText username;
    EditText password;
    private String userM;
    private String passM;
    private String Auto1;
    private String PA1;
    private String Auto2;
    private String PA2;
    private String Auto3;
    private String PA3;
    private String Auto4;
    private String PA4;
    private String Auto5;
    private String PA5;
    private String Auto6;
    private String PA6;
    private String Auto7;
    private String PA7;
    private String Auto8;
    private String PA8;
    private String Auto9;
    private String PA9;
    private String Auto10;
    private String PA10;
    private String Auto11, Auto12, Auto13, Auto14, Auto15, Auto16, Auto17, Auto18, Auto19, Auto20;
    private String PA11, PA12, PA13, PA14, PA15, PA16, PA17, PA18, PA19, PA20;
    private String Kit1, Kit2, Kit3, Kit4, Kit5, Kit6, Kit7, Kit8, Kit9, Kit10, Kit11, Kit12, Kit13, Kit14, Kit15, Kit16, Kit17, Kit18, Kit19, Kit20;
    private String PK1, PK2, PK3, PK4, PK5, PK6, PK7, PK8, PK9, PK10, PK11, PK12, PK13, PK14, PK15, PK16, PK17, PK18, PK19, PK20;
    File pdfFolder2;
    public static final String PREFS = "MyPrefFile";
    File pdfFolder;
    private String user;

    //Método onde são definidas as autenticações de Utilizadores
    public MainActivity() {
        //Master User
        userM = "AndreF";
        passM = "64882016";

        //Autocarros
        Auto1 = "Auto1";
        PA1 = "0945";
        Auto2 = "Auto2";
        PA2 = "5632";
        Auto3 = "Auto3";
        PA3 = "9654";
        Auto4 = "Auto4";
        PA4 = "3367";
        Auto5 = "Auto5";
        PA5 = "5879";
        Auto6 = "Auto6";
        PA6 = "9082";
        Auto7 = "Auto7";
        PA7 = "7531";
        Auto8 = "Auto8";
        PA8 = "4589";
        Auto9 = "Auto9";
        PA9 = "1402";
        Auto10 = "Auto10";
        PA10 = "7654";
        Auto11 = "Auto11";
        PA11 = "7781";
        Auto12 = "Auto12";
        PA12 = "7896";
        Auto13 = "Auto13";
        PA13 = "4512";
        Auto14 = "Auto14";
        PA14 = "5429";
        Auto15 = "Auto15";
        PA15 = "7285";
        Auto16 = "Auto16";
        PA16 = "7374";
        Auto17 = "Auto17";
        PA17 = "3470";
        Auto18 = "Auto18";
        PA18 = "5968";
        Auto19 = "Auto19";
        PA19 = "0779";
        Auto20 = "Auto20";
        PA20 = "4648";

        //Kits
        Kit1 = "Kits1";
        PK1 = "0284";
        Kit2 = "Kits2";
        PK2 = "7985";
        Kit3 = "Kits3";
        PK3 = "7065";
        Kit4 = "Kits4";
        PK4 = "3459";
        Kit5 = "Kits5";
        PK5 = "8275";
        Kit6 = "Kits6";
        PK6 = "2644";
        Kit7 = "Kits7";
        PK7 = "5731";
        Kit8 = "Kits8";
        PK8 = "7956";
        Kit9 = "Kits9";
        PK9 = "9468";
        Kit10 = "Kits10";
        PK10 = "1036";
        Kit11 = "Kits11";
        PK11 = "5477";
        Kit12 = "Kits12";
        PK12 = "9879";
        Kit13 = "Kits13";
        PK13 = "0155";
        Kit14 = "Kits14";
        PK14 = "2478";
        Kit15 = "Kits15";
        PK15 = "6457";
        Kit16 = "Kits16";
        PK16 = "1573";
        Kit17 = "Kits17";
        PK17 = "5849";
        Kit18 = "Kits18";
        PK18 = "7056";
        Kit19 = "Kits19";
        PK19 = "6897";
        Kit20 = "Kits20";
        PK20 = "5768";
    }

    @Override
    //Método onde se instanciam os componentes gráficos e se definem os sub-métodos do tipo "listener"
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "LOGFILE GP MIUT");
        pdfFolder2 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "LOGFILE GP MIUT");

        //Botões
        confirmButton = (Button) findViewById(R.id.check_button1);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogIn()) {
                    addInfo();
                    avancaMenu();
                } else {
                    Context context = getApplicationContext();
                    String text = "Verifique o nome de Utilizador ou a Password!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                }
            }
        });
    }

    //Adiciona informação ao ficheiro LOGFILE
    public void addInfo() {

        try {

            File infos = new File(pdfFolder + ".txt");

            Date d = new Date();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            String hora = calendar.getTime().toString().substring(11, 19);
            FileWriter fw = new FileWriter(infos, true);

            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(hora + "  LOGIN  " + user);

            out.close();
            bw.close();
            fw.close();


        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    @Override
    //Guarda informação relevante quando esta activity pára.
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Utilizador", user);
        // Commit the edits!
        editor.apply();
    }

    @Override
    //Guarda informação relevante quando esta activity se fecha.
    protected void onDestroy() {

        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String hora = calendar.getTime().toString().substring(11, 19);
        String info = hora + "  APP Fechou MainActivity  " + user;
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

    //Método que verifica se a informação inserida neste Menu corresponde a um Username e Password
    public boolean checkLogIn() {
        String u;
        String p;
        Boolean check;
        Editable a = username.getText();
        u = a.toString();

        Editable b = password.getText();
        p = b.toString();

        if (u.equals(userM) && p.equals(passM)) {

            check = true;
            user = "Master";
        } else if (u.equals(Auto1) && p.equals(PA1)) {
            check = true;
            user = "Auto1";
        } else if (u.equals(Auto2) && p.equals(PA2)) {
            check = true;
            user = "Auto2";
        } else if (u.equals(Auto3) && p.equals(PA3)) {
            check = true;
            user = "Auto3";
        } else if (u.equals(Auto4) && p.equals(PA4)) {
            check = true;
            user = "Auto4";
        } else if (u.equals(Auto5) && p.equals(PA5)) {
            check = true;
            user = "Auto5";
        } else if (u.equals(Auto6) && p.equals(PA6)) {
            check = true;
            user = "Auto6";
        } else if (u.equals(Auto7) && p.equals(PA7)) {
            check = true;
            user = "Auto7";
        } else if (u.equals(Auto8) && p.equals(PA8)) {
            check = true;
            user = "Auto8";
        } else if (u.equals(Auto9) && p.equals(PA9)) {
            check = true;
            user = "Auto9";
        } else if (u.equals(Auto10) && p.equals(PA10)) {
            check = true;
            user = "Auto10";
        } else if (u.equals(Auto11) && p.equals(PA11)) {
            check = true;
            user = "Auto11";
        } else if (u.equals(Auto12) && p.equals(PA12)) {
            check = true;
            user = "Auto12";
        } else if (u.equals(Auto13) && p.equals(PA13)) {
            check = true;
            user = "Auto13";
        } else if (u.equals(Auto14) && p.equals(PA14)) {
            check = true;
            user = "Auto14";
        } else if (u.equals(Auto15) && p.equals(PA15)) {
            check = true;
            user = "Auto15";
        } else if (u.equals(Auto16) && p.equals(PA16)) {
            check = true;
            user = "Auto16";
        } else if (u.equals(Auto17) && p.equals(PA17)) {
            check = true;
            user = "Auto17";
        } else if (u.equals(Auto18) && p.equals(PA18)) {
            check = true;
            user = "Auto18";
        } else if (u.equals(Auto19) && p.equals(PA19)) {
            check = true;
            user = "Auto19";
        } else if (u.equals(Auto20) && p.equals(PA20)) {
            check = true;
            user = "Auto20";
        } else if (u.equals(Kit1) && p.equals(PK1)) {
            check = true;
            user = "Kit1";
        } else if (u.equals(Kit2) && p.equals(PK2)) {
            check = true;
            user = "Kit2";
        } else if (u.equals(Kit3) && p.equals(PK3)) {
            check = true;
            user = "Kit3";
        } else if (u.equals(Kit4) && p.equals(PK4)) {
            check = true;
            user = "Kit4";
        } else if (u.equals(Kit5) && p.equals(PK5)) {
            check = true;
            user = "Kit5";
        } else if (u.equals(Kit6) && p.equals(PK6)) {
            check = true;
            user = "Kit6";
        } else if (u.equals(Kit7) && p.equals(PK7)) {
            check = true;
            user = "Kit7";
        } else if (u.equals(Kit8) && p.equals(PK8)) {
            check = true;
            user = "Kit8";
        } else if (u.equals(Kit9) && p.equals(PK9)) {
            check = true;
            user = "Kit9";
        } else if (u.equals(Kit10) && p.equals(PK10)) {
            check = true;
            user = "Kit10";
        } else if (u.equals(Kit11) && p.equals(PK11)) {
            check = true;
            user = "Kit11";
        } else if (u.equals(Kit12) && p.equals(PK12)) {
            check = true;
            user = "Kit12";
        } else if (u.equals(Kit13) && p.equals(PK13)) {
            check = true;
            user = "Kit13";
        } else if (u.equals(Kit14) && p.equals(PK14)) {
            check = true;
            user = "Kit14";
        } else if (u.equals(Kit15) && p.equals(PK15)) {
            check = true;
            user = "Kit15";
        } else if (u.equals(Kit16) && p.equals(PK16)) {
            check = true;
            user = "Kit16";
        } else if (u.equals(Kit17) && p.equals(PK17)) {
            check = true;
            user = "Kit17";
        } else if (u.equals(Kit18) && p.equals(PK18)) {
            check = true;
            user = "Kit18";
        } else if (u.equals(Kit19) && p.equals(PK19)) {
            check = true;
            user = "Kit19";
        } else if (u.equals(Kit20) && p.equals(PK20)) {
            check = true;
            user = "Kit20";
        } else {
            check = false;
        }
        return check;
    }

    //Método para efetuar a navegação para o Menu seguinte
    public void avancaMenu() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Utilizador", user);
        // Commit the edits!
        editor.apply();
        Intent myIntent = new Intent(this, Events.class);
        startActivity(myIntent);
        finish();

    }


}
