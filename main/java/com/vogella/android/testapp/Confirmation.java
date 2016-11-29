package com.vogella.android.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Confirmation extends Activity {

    @Override
    //Método onde se instanciam os componentes gráficos e
    //é definido o tempo de duração desta janela
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(Confirmation.this, InsertNumber.class);
                Confirmation.this.startActivity(mainIntent);
                Confirmation.this.finish();
            }
        }, 250);

    }

}
