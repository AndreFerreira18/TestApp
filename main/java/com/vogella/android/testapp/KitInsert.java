/**
 * Created by André Ferreira on 17/04/2016.
 */

package com.vogella.android.testapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.vogella.android.testapp.rest.Callback;
import com.vogella.android.testapp.rest.Rest;
import com.vogella.android.testapp.rest.models.AthleteCheckRest;
import com.vogella.android.testapp.rest.models.AthleteRest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class KitInsert extends Activity implements Callback<AthleteCheckRest>, Serializable {
    Button confirmButton;
    Button configButton;
    Button endSessButton;
    Button busButton;
    TextView infoText;
    TextView acompText;
    TextView nameText;
    TextView auxPeitoral;
    TextView peiText;
    EditText IDNumber;
    EditText athleteName;
    EditText acompNum;
    EditText genderText;
    private String nome;
    private String tshirt;
    private String genero;
    private String prova;
    File pdfFolder;
    File pdfFolder2;
    private int value;
    private static int kits_count;
    private static boolean finishing = false;
    private ArrayList<AthleteRest> lista;
    private Rest rest;
    private String user;


    @Override
    //Método onde se instanciam os componentes gráficos e se definem os sub-métodos do tipo
    //"listener" para os botões.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_number);
        DataHelper dt = new DataHelper();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dt = (DataHelper) getIntent().getSerializableExtra("AthleteList"); //Obtaining data
            lista = dt.getData();
        }

        //layout
        confirmButton = (Button) findViewById(R.id.check_button2);
        configButton = (Button) findViewById(R.id.conf_button2);
        endSessButton = (Button) findViewById(R.id.ESbutton);
        busButton = (Button) findViewById(R.id.busButton);

        IDNumber = (EditText) findViewById(R.id.idnumber);
        athleteName = (EditText) findViewById(R.id.AthleteName);
        acompNum = (EditText) findViewById(R.id.AcompNum);
        genderText = (EditText) findViewById(R.id.genderText);

        infoText = (TextView) findViewById(R.id.infoText);
        nameText = (TextView) findViewById(R.id.nameText);
        acompText = (TextView) findViewById(R.id.acompText);
        auxPeitoral = (TextView) findViewById(R.id.auxPeitoral);
        peiText = (TextView) findViewById(R.id.peitoralText);


        makeVisibleKit();


        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        user = settings.getString("Utilizador", "");
        //infoText.setVisibility(View.INVISIBLE);
        if (!finishing) {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "MiutPDFS");

        pdfFolder2 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "LOGFILE GP MIUT");

        //REST
        GsonBuilder gson = new GsonBuilder();
        gson.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.addConverterFactory(GsonConverterFactory.create(gson.create()));
        rest = new Rest(retrofit, (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE));
        rest.setApi("http://apus.uma.pt/~a2061105/v2.2/index.php/api/");


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finishing) {
                    if (!(IDNumber.toString() == null)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                    guardaValores();

                } else {
                    verificaKit();
                    finishing = false;
                }

            }

        });

        endSessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(KitInsert.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setMessage("Terminar sessão?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Date d = new Date();
                                GregorianCalendar calendar = new GregorianCalendar();
                                calendar.setTime(d);
                                String hora = calendar.getTime().toString().substring(11, 19);
                                String info = hora + "  LOGOUT  " + user;
                                String quantidade = String.valueOf(kits_count);
                                String counter = hora + "   Kits entregues: " + quantidade + "  " + user;
                                addInfo(info);
                                addInfo(counter);
                                emailLog("LOGFILE - TXT");

                                kits_count = 0;
                                finishing = false;

                                Intent myIntent = new Intent(KitInsert.this, MainActivity.class);
                                startActivity(myIntent);
                                finish();

                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.equals("Master")) {
                    avancaExtraMenu();
                }
            }
        });
    }

    //Método que define as vistas das labels para o controlo da entrega dos kits
    public void changeViews() {
        auxPeitoral.setText(R.string.peitoralTitle);
        nameText.setText(R.string.nameTitle);
        acompText.setText(R.string.shirtSize);
        athleteName.setText(nome);
        acompNum.setText(tshirt);
        if (genero.equals("M")) {
            genderText.setText(R.string.Male);
        } else if (genero.equals("F")) {
            genderText.setText(R.string.Female);
        }

        infoText.setVisibility(View.VISIBLE);
        infoText.setText(prova);

        nameText.setVisibility(View.VISIBLE);
        acompText.setVisibility(View.VISIBLE);
        auxPeitoral.setVisibility(View.VISIBLE);
        athleteName.setVisibility(View.VISIBLE);
        acompNum.setVisibility(View.VISIBLE);
        genderText.setVisibility(View.VISIBLE);

        peiText.setVisibility(View.VISIBLE);
        athleteName.setFocusable(false);
        IDNumber.setFocusable(false);
        acompNum.setFocusable(false);
        genderText.setFocusable(false);
    }

    //Método que torna visiveis as vistas das labels para o controlo da entrega dos kits
    public void makeVisibleKit() {

        infoText.setVisibility(View.INVISIBLE);
        busButton.setVisibility(View.INVISIBLE);
    }

    //Método para efetuar Navegação para outro menu
    public void avancaExtraMenu() {
        Intent myIntent = new Intent(this, AthleteList.class);
        startActivity(myIntent);
    }

    //Método que procura um determinado atleta na lista de atletas
    public int athleteSearch(int num) {
        int value = -1;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDorsalNumber() == num) {
                value = i;
                break;
            }
        }
        return value;
    }

    //Método que compõe a String para o LOGFILE
    public String stringComposerKits(int value, boolean error) {

        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String timestamp = calendar.getTime().toString().substring(11, 19);
        if (error) {
            return timestamp;
        } else {
            String dorsal = String.valueOf(lista.get(value).getDorsalNumber());
            String name = lista.get(value).getName();
            String escalao = lista.get(value).getCategory();
            String tamanho = lista.get(value).getTshirtSize();
            return "" + timestamp + " ENTREGA: [" + dorsal + "]  " + name + "  " + tamanho + "  " + "  " + escalao + "  ";
        }
    }

    //Método que guarda o peitoral inserido e os respetivos valores intermédios para a etapa
    //seguinte da confirmação
    public void guardaValores() {

        Editable edit = IDNumber.getText();
        if (IDNumber.getText().length() > 0) {
            String numbstr = edit.toString();
            int insertednum = Integer.parseInt(numbstr);

            value = athleteSearch(insertednum);
            if (value >= 0) {
                int dorsal = lista.get(value).getDorsalNumber();
                //miut
                if (dorsal < 1000) {
                    prova = "Prova: MIUT";
                } else if (dorsal > 1000 && dorsal < 2000) {
                    prova = "Prova: ULTRA";
                } else if (dorsal > 2000 && dorsal < 3000) {
                    prova = "Prova: Marathon";
                } else {
                    prova = "Prova: Mini";
                }
                //Machico
                /*if(dorsal<300){
                    prova="Prova: TR Funduras";
                }
                else{
                    prova="Prova: MTR Sidra";
                }*/

                nome = lista.get(value).getName();
                genero = lista.get(value).getGender();
                tshirt = lista.get(value).getTshirtSize();
                String a = lista.get(value).getTshirtSize();
                if (genero.equals("F") && dorsal < 2000) {
                    tshirt = sizeAdjust(a);
                }
                changeViews();
                finishing = true;
            } else {
                //INSERIR ERRO
                String s = stringComposerKits(value, true) + "  [" + insertednum + "]" + "  PEITORAL INEXISTENTE";

                addInfo(s);

                Toast.makeText(getApplicationContext(),
                        "Peitoral inexistente!", Toast.LENGTH_SHORT)
                        .show();
                Intent myIntent = new Intent(this, Error.class);
                startActivity(myIntent);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Insira um Peitoral!", Toast.LENGTH_SHORT)
                    .show();
        }


    }

    //Método que verifica as informações de um determinado peitoral e efetua o pedido para assinalar
    //a entrega de um kit, caso a mesma seja possível
    public void verificaKit() {
        //value=0;
        Editable edit = IDNumber.getText();

        if (IDNumber.getText().length() > 0) {
            String numbstr = edit.toString();
            int insertednum = Integer.parseInt(numbstr);
            //value = athleteSearch(insertednum);
            if (value >= 0) {
                if (lista.get(value).getStatus().equals("DNF")) {

                    String info = stringComposerKits(value, true) + " ERRO: [" + insertednum + "]  " + lista.get(value).getStatus();
                    addInfo(info);

                    Toast.makeText(getApplicationContext(),
                            "O Atleta não terminou a prova. Não pode receber Kit!", Toast.LENGTH_SHORT)
                            .show();
                    Intent myIntent = new Intent(this, Error.class);
                    startActivity(myIntent);

                } else if (lista.get(value).getStatus().equals("DNS")) {

                    String info = stringComposerKits(value, true) + " ERRO: [" + insertednum + "]  " + lista.get(value).getStatus();
                    addInfo(info);
                    Toast.makeText(getApplicationContext(),
                            "O Atleta não iniciou a prova. Não pode receber Kit!", Toast.LENGTH_SHORT)
                            .show();
                    Intent myIntent = new Intent(this, Error.class);
                    startActivity(myIntent);

                } else if (lista.get(value).getStatus().equals("DSQ")) {

                    String info = stringComposerKits(value, true) + " ERRO: [" + insertednum + "]  " + lista.get(value).getStatus();
                    addInfo(info);
                    Toast.makeText(getApplicationContext(),
                            "O Atleta foi desclassificado. Não pode receber Kit!", Toast.LENGTH_SHORT)
                            .show();
                    Intent myIntent = new Intent(this, Error.class);
                    startActivity(myIntent);
                } else if (!lista.get(value).getStatus().equals("FIN")) {

                    String info = stringComposerKits(value, true) + " ERRO: [" + insertednum + "]  " + lista.get(value).getStatus();
                    addInfo(info);
                    Toast.makeText(getApplicationContext(),
                            "O Atleta ainda não terminou a prova!", Toast.LENGTH_SHORT)
                            .show();
                    Intent myIntent = new Intent(this, Error.class);
                    startActivity(myIntent);

                } else {
                    rest.setAthleteChecked(lista.get(value).getId(), "kit_finisher", this);
                }
            } else {
                String s = stringComposerKits(value, true) + "  [" + insertednum + "]" + "  PEITORAL INEXISTENTE";
                addInfo(s);
                Toast.makeText(getApplicationContext(),
                        "Peitoral inexistente!", Toast.LENGTH_SHORT)
                        .show();
                Intent myIntent = new Intent(this, Error.class);
                startActivity(myIntent);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Insira um Peitoral!", Toast.LENGTH_SHORT)
                    .show();
            Intent myIntent = new Intent(this, Error.class);
            startActivity(myIntent);
        }
    }

    @Override
    //Guarda informação relevante quando esta activity se fecha.
    protected void onDestroy() {
        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String hora = calendar.getTime().toString().substring(11, 19);
        String info = hora + "  APP Fechou KitInsert  " + user;
        addInfo(info);
        super.onDestroy();
    }

    @Override
    //Método que é chamado quando o Botão Android "Recuar" é pressionado
    public void onBackPressed() {
        new AlertDialog.Builder(KitInsert.this)
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setMessage("Dados Incorrectos. Voltar atrás?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishing = false;
                        Intent myIntent = new Intent(KitInsert.this, InsertNumber.class);
                        startActivity(myIntent);

                    }
                })
                .setNegativeButton("Não", null)
                .show();

    }

    @Override
    //Este método é chamado quando é recebi a resposta positiva da API relativamente ao pedido
    //para assinalar a entrega de um kit.
    public void onResponse(AthleteCheckRest data) {

        String info = stringComposerKits(value, false).substring(0, 8);
        info += "  [" + lista.get(value).getDorsalNumber() + "]";
        info += "  ENTREGUE";
        addInfo(info);
        kits_count++;
        Intent myIntent = new Intent(this, Confirmation.class);
        startActivity(myIntent);

    }

    @Override
    //Guarda informação relevante quando esta activity pára.
    protected void onStop() {
        super.onStop();


    }

    @Override
    //Este método é chamado quando é recebi a resposta negativa da API relativamente ao pedido
    //para assinalar a entrega de um kit.
    public void onError(com.vogella.android.testapp.rest.Error error) {

        String info = stringComposerKits(value, true) + " ERRO: [" + lista.get(value).getDorsalNumber() + "]  KIT JÁ ENTREGUE";
        addInfo(info);
        Toast.makeText(getApplicationContext(),
                "O Atleta já recebeu Kit! Não entregar Kit!", Toast.LENGTH_SHORT)
                .show();
        Intent myIntent = new Intent(this, Error.class);
        startActivity(myIntent);


    }

    //Método que envia o ficheiro LOGFILE
    public void emailLog(String subject) {

        Mail m = new Mail("gestoratletasmiut@gmail.com", "Miut2016");

        String[] toArr = {"andrefferreira@outlook.pt"}; //emarques@uma.pt madeiraultratrail@gmail.com
        m.setTo(toArr);
        m.setFrom("gestoratletasmiut@gmail.com");
        m.setSubject(subject);
        m.setBody("LOGFILE do equipamento utilizado por: " + user + ". Consulte o ficheiro em anexo para mais informações." +
                "\n\n" +
                "Cumprimentos," +
                "\nAndré Ferreira" +
                "\nGestão de Atletas MIUT");

        File infos = new File(pdfFolder2 + ".txt");

        try {
            m.addAttachment(infos.getAbsolutePath());

            if (m.send()) {
                Toast.makeText(getApplicationContext(), "E-mail enviado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Falha no envio do E-mail.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email", e);
        }

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

    //Método para ajustar o tamanho das t-shirts MIUT - género feminino.
    public String sizeAdjust(String a) {
        AtomicReference<String> aux = new AtomicReference<>();
        switch (a) {
            case "XS":
                aux.set("XS");
                break;
            case "S":
                aux.set("XS");
                break;
            case "M":
                aux.set("S");
                break;
            case "L":
                aux.set("M");
                break;
            case "XL":
                aux.set("L");
                break;
            case "XXL":
                aux.set("XL");
                break;
            case "XXXL":
                aux.set("XXL");
                break;
            default:
                aux.set("");

        }
        return aux.get();
    }
}
