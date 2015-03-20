package com.eduardo.circletetris;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    static TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (TextView) findViewById(R.id.textView);
        Peca.setMainActivity(this);


        Button botaoEsq = (Button) findViewById(R.id.botaoEsq);
        Button botaoDir = (Button) findViewById(R.id.botaoDir);
        Button botaoMeio = (Button) findViewById(R.id.botaoMeio);

        botaoEsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Peca.esquerda();
            }
        });
        botaoDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Peca.direita();
            }
        });
        botaoMeio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Peca.gira();
            }
        });
    }

    public static void atualizaTexto(String str) {
        texto.append(str);
    }

    public static void mensagem(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
