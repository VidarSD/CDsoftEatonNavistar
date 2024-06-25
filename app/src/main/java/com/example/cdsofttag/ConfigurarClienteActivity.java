package com.example.cdsofttag;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.xslf.usermodel.XSLFRelation;

public class ConfigurarClienteActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnadd;
    Button btndelete;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cliente);
        btnadd = findViewById(R.id.btnaddlciente);
        btndelete= findViewById(R.id.btneliminateclient);
        btndelete.setOnClickListener(this);
        btnadd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnaddlciente)
        {
            agregarCliente();

        }else if (v.getId() == R.id.btneliminateclient)
        {

            startActivity(new Intent(ConfigurarClienteActivity.this,GenerateReportActivity.class));
        }
    }

    private void agregarCliente() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ConfigurarClienteActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_agregarcliente, null);
        EditText txtnewClient = mView.findViewById(R.id.txtnewclient);
        Button btninsertclient = mView.findViewById(R.id.btninsertarcliente);
        Button btncancel = mView.findViewById(R.id.btncancelnsertcliente);

        builder.setView(mView);
        AlertDialog alertDialog = builder.create();

        btninsertclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cliente = txtnewClient.getText().toString();
                if (!cliente.isEmpty())
                {
                    myDatabaseHelper = new MyDatabaseHelper(getApplicationContext());
                    myDatabaseHelper.addCliente(cliente);
                    Toast.makeText(ConfigurarClienteActivity.this, "Cliente Agregado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ConfigurarClienteActivity.this,MenuActivityApp.class));
                    alertDialog.dismiss();
                }else
                {
                    Toast.makeText(ConfigurarClienteActivity.this, "Complete la informacion", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        alertDialog.show();

    }

    private void eliminarCliente() {


    }
}