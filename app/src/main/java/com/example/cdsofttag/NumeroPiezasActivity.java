package com.example.cdsofttag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NumeroPiezasActivity extends AppCompatActivity implements View.OnClickListener {

    EditText TxtCantPiezas;
    EditText TxtNumPartPat;
    Button btnSetCantPz;


    int SelectedClient ;
    int CantPiezas;
    String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numero_piezas);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            SelectedClient = bundle.getInt("id_cliente");
            User = bundle.getString("user");

        }

        TxtCantPiezas = findViewById(R.id.txtcantidadPiezas);
        TxtNumPartPat = findViewById(R.id.txtNumeroPartePat);
        btnSetCantPz = findViewById(R.id.btnCantidadPiezas);


        btnSetCantPz.setOnClickListener(this);


        TxtCantPiezas.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    if(!(TxtCantPiezas.getText().toString().isEmpty()) && (TxtNumPartPat.getText().toString().isEmpty()) )
                    {
                        CantPiezas = Integer.parseInt(TxtCantPiezas.getText().toString());
                        String numpte = TxtNumPartPat.getText().toString();
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(NumeroPiezasActivity.this, Comparacion.class);
                        bundle.putInt("id_cliente",SelectedClient);
                        bundle.putString("np",numpte);
                        bundle.putString("user", User);
                        bundle.putInt ("piezas",CantPiezas);

                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();


                    }
                    else {
                        Toast.makeText(NumeroPiezasActivity.this, "Comlete la informacion", Toast.LENGTH_SHORT).show();
                    }
                }
                    return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.btnCantidadPiezas)
            {
               // Toast.makeText(this, User, Toast.LENGTH_SHORT).show();

                if(!TxtCantPiezas.getText().toString().isEmpty())
                {
                   CantPiezas = Integer.parseInt(TxtCantPiezas.getText().toString());

                    CantPiezas = Integer.parseInt(TxtCantPiezas.getText().toString());
                    String numpte = TxtNumPartPat.getText().toString();
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(NumeroPiezasActivity.this, Comparacion.class);
                    bundle.putInt("id_cliente",SelectedClient);
                    bundle.putString("np",numpte);
                    bundle.putString("user", User);
                    bundle.putInt ("piezas",CantPiezas);

                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();


                }
            }
    }
}