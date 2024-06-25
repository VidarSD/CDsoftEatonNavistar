package com.example.cdsofttag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;

public class MenuActivityApp extends AppCompatActivity implements View.OnClickListener {

    CardView btnEscanear;
    CardView btnConfig;
    String user= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_app);

        btnEscanear= findViewById(R.id.cardView);
        btnConfig = findViewById(R.id.btnConfig);
        btnEscanear.setOnClickListener(this);
        btnConfig.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
                user = bundle.getString("user");
            if (bundle.getBoolean("isAdmin"))
            {   user = bundle.getString("user");
                btnConfig.setVisibility(View.VISIBLE);
            }
          //  Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cardView)
        {

            Bundle bundle = new Bundle();
            Intent intent = new Intent(this, SelectClienteActivity.class);
            bundle.putString("user",user);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (v.getId()==R.id.btnConfig)
        {
            startActivity(new Intent(MenuActivityApp.this,ConfigurarClienteActivity.class));
            finish();
        }
    }
}