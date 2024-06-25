package com.example.cdsofttag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectClienteActivity extends AppCompatActivity implements View.OnClickListener {
    MyDatabaseHelper myDB;
    ListView lvclientes;
    TextView txtselectedcliente;
    Button btnselectcliente;
    String user = "";
    int selectedcliente = -1;
//    ArrayList <String> clientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cliente);
        lvclientes = findViewById(R.id.LVclientes);
        txtselectedcliente = findViewById(R.id.txtselectedcliente);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {

                user = bundle.getString("user");

          //  Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        }


        myDB = new MyDatabaseHelper(getApplicationContext());


//        myDB.addCliente("Cliente 1");
//        myDB.addCliente("Cliente 2");
//        myDB.addCliente("Cliente 3");

       ArrayList <String> clientes = new ArrayList<String>();
     //   clientes.add("Navistar");

        Cursor cursor = myDB.readCliente();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No se encontraron clientes", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext())
            {
                clientes.add(cursor.getString(0));

            }
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.lv_clientes, R.id.txtNombCli, clientes);
            lvclientes.setAdapter(adapter);

            lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setSelected(true);
                    selectedcliente = position+1;

                   // Toast.makeText(SelectClienteActivity.this,String.valueOf(selectedcliente), Toast.LENGTH_SHORT).show();
                    txtselectedcliente.setText( "Cliente seleccionado : "+ parent.getItemAtPosition(position).toString());

                }
            });
        }


        btnselectcliente = findViewById(R.id.btn_select_cliente);
        btnselectcliente.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_select_cliente)
        {
                if (selectedcliente < 0 )
                {
                    Toast.makeText(this, "Selecione un cliente para continuar", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(SelectClienteActivity.this, Comparacion.class);
                    bundle.putInt("id_cliente",selectedcliente);
                    bundle.putString("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
        }
    }
}