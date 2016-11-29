package com.vogella.android.testapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.vogella.android.testapp.rest.Callback;
import com.vogella.android.testapp.rest.Rest;
import com.vogella.android.testapp.rest.models.AthleteRest;
import com.vogella.android.testapp.rest.models.CompetitionRest;
import com.vogella.android.testapp.rest.models.EventRest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class InsertNumber extends Activity implements Callback<EventRest>, Serializable {
    Button confirmButton;
    Button configButton;
    Button endSessButton;
    Button lessButton;
    Button moreButton;
    Button busButton;
    TextView peiText;
    TextView infoText;
    TextView nameText;
    TextView acompText;
    TextView auxPeitoral;
    EditText athleteName;
    EditText IDNumber;
    EditText acompNum;
    private String nome;
    private int acompanhantes;
    File pdfFolder;
    File pdfFolder2;
    private String autocarro;
    private static int pdf_count = 1;
    private static int index = 0;
    private static int regBus = 0;
    private static int acompTotal = 0;
    private static int athleteTotal = 0;
    private static boolean finishing = false;
    private static String[] infoBus = new String[100];
    private static int[] dorsais = new int[100];
    private static ArrayList<AthleteRest> lista;
    private static boolean first = true;
    private String user;
    private String selection;
    private String password;
    private int id;
    Intent mudanca;


    public InsertNumber() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    //Método onde se instanciam os componentes gráficos e se definem os sub-métodos do tipo
    //"listener" para os botões. É também estabelecida a ligação à API
    //para ter acesso à lista de atletas
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_number);
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS, 0);
        user = settings.getString("Utilizador", "");
        selection = settings.getString("Seleccao", "");
        password = settings.getString("Password", "");
        id = settings.getInt("Id", 0);

        //layout - BOTÕES
        confirmButton = (Button) findViewById(R.id.check_button2);
        configButton = (Button) findViewById(R.id.conf_button2);
        endSessButton = (Button) findViewById(R.id.ESbutton);
        busButton = (Button) findViewById(R.id.busButton);
        lessButton = (Button) findViewById(R.id.lessButton);
        moreButton = (Button) findViewById(R.id.moreButton);
        //layout - TEXTO
        nameText = (TextView) findViewById(R.id.nameText);
        infoText = (TextView) findViewById(R.id.infoText);
        acompText = (TextView) findViewById(R.id.acompText);
        peiText = (TextView) findViewById(R.id.peitoralText);
        auxPeitoral = (TextView) findViewById(R.id.auxPeitoral);
        //layout - CAMPOS INSERÇÃO
        IDNumber = (EditText) findViewById(R.id.idnumber);
        acompNum = (EditText) findViewById(R.id.AcompNum);
        athleteName = (EditText) findViewById(R.id.AthleteName);
        IDNumber.setText(id);

        if (first) {
            lista = new ArrayList<>();
        }

        makeVisibleBus();


        autocarro = "";

        pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "MiutPDFS");
        pdfFolder2 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "LOGFILE GP MIUT");

        mudanca = new Intent(this, KitInsert.class);

        //carregamento de atletas
        if (first || selection.equals("kit")) {

            //REST
            GsonBuilder gson = new GsonBuilder();
            gson.setDateFormat("yyyy-MM-dd HH:mm:ss");
            Retrofit.Builder retrofit = new Retrofit.Builder();
            retrofit.addConverterFactory(GsonConverterFactory.create(gson.create()));
            Rest rest = new Rest(retrofit, (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE));
            rest.setApi("http://apus.uma.pt/~a2061105/v2.2/index.php/api/");
            rest.getEvent(33, "sidra", this);
//            //rest.getEvent(23,"20miut16",this);
//            rest.getEvent(id,password,this);
            first = false;
        }


        if (!finishing) {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finishing) {
                    if (!(IDNumber.toString() == null)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                    guardaValores();
                    acompNum.setFocusable(true);
                    acompNum.requestFocus();

                } else {
                    try {
                        verificaPeitoral();
                    } catch (DocumentException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finishing = false;
                }
            }

        });

        endSessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(InsertNumber.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setMessage("Terminar sessão?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pdf_count = 1;
                                finishing = false;
                                Date d = new Date();
                                GregorianCalendar calendar = new GregorianCalendar();
                                calendar.setTime(d);
                                String hora = calendar.getTime().toString().substring(11, 19);
                                String info = hora + "  LOGOUT  " + user;
                                addInfo(info);
                                emailLog("LOGFILE - TXT");
                                Intent myIntent = new Intent(InsertNumber.this, MainActivity.class);
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
                }/*
            else {
                autocarro="";
                    try {
                        imprimeInfoBus(false);
                        makeVisibleBus();
                        int aux = pdf_count -1;
                        File myFile =  new File(pdfFolder+" "+user+" "+aux+".pdf");
                        emailNote(myFile, "Autocarros - PDF");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

            }*/

            }
        });
        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(InsertNumber.this);
                final EditText edittext = new EditText(InsertNumber.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    edittext.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                alert.setMessage("Matrícula:");
                alert.setTitle("Autocarro cheio?");
                alert.setView(edittext);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        autocarro = edittext.getText().toString();
                        try {
                            Date d = new Date();
                            GregorianCalendar calendar = new GregorianCalendar();
                            calendar.setTime(d);
                            String hora = calendar.getTime().toString().substring(11, 19);
                            String info = hora + "  AUTOCARRO " + autocarro + " CHEIO  " + user + "  " + regBus + " pessoas.";
                            addInfo(info);
                            imprimeInfoBus(true);
                            makeVisibleBus();
                            /*int aux = pdf_count -1;
                            File myFile =  new File(pdfFolder+" "+user+" "+aux+".pdf");
                            emailNote(myFile, "Autocarros - PDF");*/

                        } catch (IOException | DocumentException e) {
                            e.printStackTrace();
                        }

                    }
                });
                alert.setNegativeButton("Não", null);

                alert.show();
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable edit = acompNum.getText();
                String num = edit.toString();
                int a = Integer.parseInt(num);
                a++;
                acompNum.setText(String.valueOf(a));
            }
        });

        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable edit = acompNum.getText();
                String num = edit.toString();
                int a = Integer.parseInt(num);
                if (a > 0) {
                    a--;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Valor mínimo de acompanhantes.", Toast.LENGTH_SHORT)
                            .show();
                }
                acompNum.setText(String.valueOf(a));
            }
        });
    }

    //Verifica se um peitoral já existe na lista de peitorais
    public boolean dorsalAlreadyExists(int num) {
        for (int dorsal : dorsais) {
            if (num == dorsal) {
                return true;
            }
        }
        return false;
    }

    //Método que guarda o peitoral inserido e os respetivos valores intermédios para a etapa
    //seguinte da confirmação
    public void guardaValores() {
        int value;
        Editable edit = IDNumber.getText();
        if (IDNumber.getText().length() > 0) {
            String numbstr = edit.toString();
            int insertednum = Integer.parseInt(numbstr);
            value = athleteSearch(insertednum);
            if (0 <= value) {
                if (!dorsalAlreadyExists(insertednum)) {
                    nome = lista.get(value).getName();
                    acompanhantes = lista.get(value).getTransportBuddy();
                    changeViews();
                    finishing = true;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "O Atleta [" + insertednum + "] já entrou no autocarro.", Toast.LENGTH_SHORT)
                            .show();

                }
            } else {
                //INSERIR ERRO
                String s = stringComposerBus(value, 0, true) + "  [" + insertednum + "]" + "  PEITORAL INEXISTENTE";
                infoBus[index] = s;
                addInfo(s);
                index++;
                Toast.makeText(getApplicationContext(),
                        "Peitoral Inexistente!", Toast.LENGTH_SHORT)
                        .show();
                Intent myIntent = new Intent(this, Error.class);
                startActivity(myIntent);

            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Insira um Peitoral!", Toast.LENGTH_SHORT)
                    .show();
        }
        if (finishing) {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

    }

    //Método que define as vistas das labels para o controlo dos autocarros
    public void changeViews() {
        auxPeitoral.setText(R.string.peitoralTitle);
        nameText.setText(R.string.nameTitle);
        acompText.setText(R.string.companyTitle);
        athleteName.setText(nome);
        acompNum.setText(String.valueOf(acompanhantes));

        nameText.setVisibility(View.VISIBLE);
        acompText.setVisibility(View.VISIBLE);
        auxPeitoral.setVisibility(View.VISIBLE);
        athleteName.setVisibility(View.VISIBLE);
        acompNum.setVisibility(View.VISIBLE);
        lessButton.setVisibility(View.VISIBLE);
        moreButton.setVisibility(View.VISIBLE);
        peiText.setVisibility(View.INVISIBLE);


    }

    //Método que torna visiveis as vistas das labels para o controlo dos autocarros
    public void makeVisibleBus() {
        if (regBus == 0) {
            infoText.setText(R.string.emptyBus);
        } else if (regBus == 1) {
            infoText.setText("Já entrou " + regBus + " pessoa.");
        } else {
            infoText.setText("Já entraram " + regBus + " pessoas.");
        }
        infoText.setVisibility(View.VISIBLE);
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

    //Método que compõe a String para o relatório
    public String stringComposerBus(int value, int acomp, boolean error) {

        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String timestamp = calendar.getTime().toString().substring(11, 19);

        if (error) {
            return timestamp;
        } else {

            String dorsal = String.valueOf(lista.get(value).getDorsalNumber());
            String name = lista.get(value).getName();
            String team = lista.get(value).getClub();
            String escalao = lista.get(value).getCategory();
            String acompa = String.valueOf(acomp);
            return "" + timestamp + " ENTRADA: [" + dorsal + "]  " + name + " ACOMP: " + acompa + " " + team + "   " + escalao;
        }
    }

    //Método que verifica as informações de um determinado peitoral
    public void verificaPeitoral() throws FileNotFoundException, DocumentException {
        int acomp = 0;
        int value;
        Editable edit = IDNumber.getText();
        Editable edit2 = acompNum.getText();
        if (0 < IDNumber.getText().length()) {
            String numbstr = edit.toString();
            String acompstr = edit2.toString();
            int insertednum = Integer.parseInt(numbstr);
            value = athleteSearch(insertednum);
            if (0 <= value) {

                if (!acompstr.equals("")) {
                    acomp = Integer.parseInt(acompstr);
                    regBus = regBus + acomp;
                    acompTotal = acompTotal + acomp;
                }
                dorsais[index] = lista.get(value).getDorsalNumber();
                infoBus[index] = stringComposerBus(value, acomp, false);
                String info = stringComposerBus(value, acomp, false).substring(0, 8);
                info += "  [" + lista.get(value).getDorsalNumber() + "]";
                info += "  ENTROU  +  " + String.valueOf(acomp);
                addInfo(info);
                regBus++;
                athleteTotal++;
                index++;
                Intent myIntent = new Intent(this, Confirmation.class);
                startActivity(myIntent);


            } else {
                String s = stringComposerBus(value, 0, true) + "  [" + insertednum + "]" + "  PEITORAL INEXISTENTE";
                infoBus[index] = s;
                addInfo(s);
                index++;
                Intent myIntent = new Intent(this, Error.class);
                startActivity(myIntent);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Insira um Peitoral!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //Método para partição da lista de peitorais com vista à ordenação
    int partition(int arr[], int left, int right) {

        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        return i;
    }

    //Método para ordenação da lista de peitorais
    void quickSort(int arr[], int left, int right) {

        int index = partition(arr, left, right);

        if (left < index - 1)

            quickSort(arr, left, index - 1);

        if (index < right)

            quickSort(arr, index, right);

    }

    //Método que compila o relatório de controlo das entradas nos autocarros.
    public void imprimeInfoBus(boolean full) throws DocumentException, IOException {
        /*if(isNetworkAvailable()) {
            //LOGO MIUT
            String logoMiut = "http://corredoresanonimos.pt/wp-content/uploads/2015/04/miut.jpg";
            Image miut = Image.getInstance(new URL(logoMiut));
            miut.setAbsolutePosition(200f, 700f);
            miut.scaleAbsolute(120f, 125f);
            //LOGO CMOF
            String logoCMOF = "http://www.cmofunchal.org/cmof/images/logos/logocmof400px.png";
            Image cmof = Image.getInstance(new URL(logoCMOF));
            cmof.setAbsolutePosition(50f, 710f);
            cmof.scaleAbsolute(110f, 90f);
            //LOGO UMA
            String logoUMa = "http://www.uma.pt/portal/imagens/logo/download/logo.gif";
            Image uma = Image.getInstance(new URL(logoUMa));
            uma.setAbsolutePosition(320f, 685f);
            uma.scaleAbsolute(300f, 120f);
        }*/

        File myFile = new File(pdfFolder + " " + user + " " + pdf_count + ".pdf");
        FileOutputStream output = new FileOutputStream(myFile);
        Document document = new Document();
        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String day = calendar.getTime().toString().substring(8, 10);
        quickSort(dorsais, 0, dorsais.length - 1);
        try {
            PdfWriter.getInstance(document, output);
            Font auxText = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font fontText = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font title = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font btitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font header = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.UNDERLINE | Font.BOLD);
            Paragraph footer = new Paragraph("CMOF Timing System  ", auxText);
            footer.setAlignment(Element.ALIGN_CENTER);
            Paragraph bigT = new Paragraph("Gestão de Atletas", btitle);
            Paragraph bigVal = new Paragraph("Autocarros", btitle);
            bigT.setAlignment(Element.ALIGN_CENTER);
            bigVal.setAlignment(Element.ALIGN_CENTER);
            document.open();
            document.add(new Paragraph("\n")); //document.add(new Paragraph("\n")); document.add(new Paragraph("\n"));
            // document.add(new Paragraph("\n")); document.add(new Paragraph("\n")); document.add(new Paragraph("\n"));
            document.add(bigT);
            document.add(bigVal);
            /*if(isNetworkAvailable(){
                document.add(cmof);
             document.add(miut); document.add(uma); }*/

            document.add(new Paragraph(day + " Abril 2016", header));
            document.add(new Phrase("Utilizador: ", title));
            document.add(new Phrase(user));
            document.add(new Paragraph(""));
            document.add(new Phrase("Autocarro: ", title));
            document.add(new Phrase(autocarro));
            document.add(new Paragraph(""));
            document.add(new Phrase("Atletas: ", title));
            document.add(new Phrase("" + athleteTotal));
            document.add(new Paragraph(""));
            document.add(new Phrase("Acompanhantes: ", title));
            document.add(new Phrase("" + acompTotal));
            document.add(new Paragraph(""));
            document.add(new Phrase("Número total de entradas: ", title));
            document.add(new Phrase("" + regBus));

            if (infoBus.length > 0) {
                document.add(new Paragraph("Lista de dorsais: ", title));
                for (int dorsal : dorsais) {
                    String sentence = String.valueOf(dorsal);
                    if (!sentence.equals("0")) {
                        document.add(new Phrase("[" + sentence + "]   "));
                        //document.add(new Paragraph(""));
                    }
                }
                document.add(new Paragraph("Lista detalhada: ", title));
                for (int i = 0; i < index; i++) {
                    String sentence = infoBus[i];
                    document.add(new Paragraph(sentence, fontText));
                    document.add(new Paragraph(""));
                }
            } else {
                document.add(new Chunk("Está vazia"));
            }
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(footer);
            Paragraph p = new Paragraph(myFile.getAbsolutePath(), auxText);
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (!full) {
            pdf_count++;
        } else {
            pdf_count++;
            index = 0;
            acompTotal = 0;
            athleteTotal = 0;
            regBus = 0;
            for (int i = 0; i < infoBus.length; i++) {
                infoBus[i] = "";
            }
            for (int i = 0; i < dorsais.length; i++) {
                dorsais[i] = 0;
            }
        }


    }

    @Override
    //Guarda informação relevante quando esta activity pára.
    protected void onStop() {
        super.onStop();

    }

    public void emailNote(File file, String subject) {

        Mail m = new Mail("gestoratletasmiut@gmail.com", "Miut2016");
        String[] toArr = {"andrefferreira@outlook.pt"}; //emarques@uma.pt madeiraultratrail@gmail.com
        m.setTo(toArr);
        m.setFrom("gestoratletasmiut@gmail.com");
        m.setSubject(subject);
        m.setBody("Foi gerado um relatório pelo Utilizador: " + user + ". Consulte o ficheiro em anexo para mais informações." +
                "\n\n" +
                "Cumprimentos," +
                "\nAndré Ferreira" +
                "\nGestão de Atletas MIUT");


        try {
            m.addAttachment(file.getAbsolutePath());

            if (m.send()) {
                Toast.makeText(getApplicationContext(), "E-mail enviado com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Falha no envio do E-mail.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(InsertNumber.this, "Falha no envio do E-mail.", Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email", e);
        }


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

    //Método que verifica a ligação à Internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    //Guarda informação relevante quando esta activity se fecha.
    protected void onDestroy() {
        first = false;
        Date d = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String hora = calendar.getTime().toString().substring(11, 19);
        String info = hora + "  APP Fechou InsertNumber  " + user;
        addInfo(info);
        super.onDestroy();
    }

    @Override
    //Método que é chamado quando o Botão Android "Recuar" é pressionado
    public void onBackPressed() {
        new AlertDialog.Builder(InsertNumber.this)
                .setIcon(android.R.drawable.ic_dialog_alert)

                .setMessage("Dados Incorrectos. Voltar atrás?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishing = false;
                        Intent myIntent = new Intent(InsertNumber.this, InsertNumber.class);
                        startActivity(myIntent);

                    }
                })
                .setNegativeButton("Não", null)
                .show();

    }

    @Override
    //Este método é chamado quando é recebi a resposta da API relativamente ao pedido
    //da lista de atletas
    public void onResponse(EventRest data) {

        CompetitionRest[] comp = data.getCompetitions();
        for (CompetitionRest comp1 : comp) {
            AthleteRest[] athletes = comp1.getAthletes();
            Collections.addAll(lista, athletes);

        }


        if (selection.equals("kit")) {
            finishing = false;
            DataHelper dt = new DataHelper();
            dt.setData(lista);
            Intent myIntent = new Intent(this, KitInsert.class);
            myIntent.putExtra("AthleteList", dt);
            startActivity(myIntent);
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

    @Override
    public void onError(com.vogella.android.testapp.rest.Error error) {

    }

}


