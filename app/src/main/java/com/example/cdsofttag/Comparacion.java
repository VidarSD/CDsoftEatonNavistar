package com.example.cdsofttag;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comparacion extends AppCompatActivity implements View.OnClickListener {
    AlertDialog alertDialog;

    ProgressBar progressBar;
    MyDatabaseHelper myDb;
    EditText txtEatonTag;
    EditText txtProveTag;
    TextView txtPz;
    TextView currentPN;
    TextView txtcapa;
    TextView txtContador;
    String user="";
    String npPat = "";
    String np="";
    int cliente=-1;
    int piezas = 0;

    int CantPZ = 0;
    int contador = 0;
    boolean isTrue = true;

    int capa =1;
    String s_eatontag ;
    String s_provetag ;
    String pnEaton, pnProve, nsEaton,nsProve;
    long embarque_id;

    Button btn_finalizar;

//    105550-CL-70,FE2402220092
//    105510-5,FE2401260128
//    105550-CL-65,FE2312130073
//    C
//    105510-5
//    4476552C91
//    105550-CL-65  4298420C91
//    105550-CL-70  4298420C91

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparacion);
        txtEatonTag=findViewById(R.id.txteatontag);
        txtProveTag = findViewById(R.id.txtprovetag);
        txtPz = findViewById(R.id.txtContadorPiezas);
        btn_finalizar = findViewById(R.id.btnfinalizarescaneo);
        txtcapa = findViewById(R.id.txtCapa);
        progressBar = findViewById(R.id.progressBarContador);
        txtContador = findViewById(R.id.txtprogresspz);
        currentPN = findViewById(R.id.txtCurrentNP);


        btn_finalizar.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        progressBar.setProgress(0);
        txtEatonTag.setShowSoftInputOnFocus(false);




        txtEatonTag.requestFocus();
        File file = new File(Environment.getExternalStorageDirectory() ,"Navistar");
        if (!file.exists())
        {
            file.mkdir();
            //Toast.makeText(this, "Folder Creado", Toast.LENGTH_SHORT).show();
        }
        
        

        if (bundle != null)
        {


                user = bundle.getString("user");
                cliente = bundle.getInt("id_cliente");
                //CantPZ = bundle.getInt("piezas");
             //   np = bundle.getString("np");
              //  progressBar.setMax(CantPZ);

                txtContador.setText("0 / " + String.valueOf(CantPZ));
           // Toast.makeText(this, user + String.valueOf(cliente), Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, "Error De datos. E.Bundle.Empty()", Toast.LENGTH_SHORT).show();
        }

//
//        currentPN.setText(getNP(np));
//        npPat = getNP(np);


         myDb = new MyDatabaseHelper(getApplicationContext());
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        Date date2 = new Date();
        embarque_id = myDb.addEmbarque(dateFormat.format(date),dateFormat2.format(date2),user,cliente);

        if (embarque_id == -1)
        {
            Toast.makeText(this, "ERROR : Not Valid Insert", Toast.LENGTH_SHORT).show();
        }else
        {
          //  Toast.makeText(this, "Succed" + String.valueOf(embarque_id), Toast.LENGTH_SHORT).show();
        }


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String answerString = txtEatonTag.getText().toString();

                //and now we make a Toast
                //modify "yourActivity.this" with your activity name .this
              //  Toast.makeText(Comparacion.this,"The string from EditText is: "+answerString,Toast.LENGTH_SHORT).show();
                txtProveTag.requestFocus();

            }
        };

        txtEatonTag.addTextChangedListener(textWatcher);

        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                try {

                    String tag = txtEatonTag.getText().toString();
                    //  Toast.makeText(Comparacion.this,"The string from EditText is: "+tag,Toast.LENGTH_SHORT).show();

                    Log.v("Cadena", tag);
                    s_eatontag = txtEatonTag.getText().toString();
                    s_provetag = txtProveTag.getText().toString();


                    if (!((s_eatontag.isEmpty()) && (s_provetag.isEmpty()))) {

                        if (ValidarTag(s_eatontag, s_provetag)) {

                            nsEaton = getSN(s_eatontag);
                            nsProve = getSN(s_provetag);

                            pnEaton = getNP(s_eatontag);
                            pnProve = getNP(s_provetag);
                            // Toast.makeText(Comparacion.this, "sison", Toast.LENGTH_SHORT).show();

                            Log.v("NPTE ", pnEaton + "|" + pnProve + "|" + np);
                            //                            if (npPat.equals(pnEaton) && npPat.equals(pnProve))
                            //                            {

                            //                                    contador++;
                            //                                    if (contador < CantPZ) {
                            //    //                                contador ++;
                            //                                        txtContador.setText(contador + "/ " + CantPZ);
                            //                                        progressBar.setProgress(contador);
                            //                                capa +=1;
                            //                                if ((capa >3 ))
                            //                                {
                            //                                    Toast.makeText(Comparacion.this, "Se alcanzo el limite de capas , Finalize el escaneo", Toast.LENGTH_SHORT).show();
                            //                                    return  false;
                            //                                }
                            //                                piezas = 1;
                            //
                            txtProveTag.setText("");
                            txtEatonTag.setText("");
                            isTrue = true;

                            txtProveTag.clearFocus();
                            txtEatonTag.requestFocus();

                            //                                    } else {
                            //                                        txtContador.setText(contador + "/ " + CantPZ);
                            //                                        Toast.makeText(Comparacion.this, "Todas las piezas han sido escaneadas, Termine el escaneo ", Toast.LENGTH_SHORT).show();
                            //                                        progressBar.setProgress(contador);
                            //                                        txtEatonTag.setEnabled(false);
                            //                                        txtProveTag.setEnabled(false);
                            //    //                                piezas += 1;
                            //    //                                txtPz.setText(String.valueOf(piezas)+"/4");
                            //
                            //
                            //                                    }
                            long result = myDb.addPieza(nsEaton,
                                    nsProve,
                                    String.valueOf(embarque_id),
                                    pnProve,
                                    "PASA"
                            );
                            if (result == -1) {
                                Log.e("DATABASE ERROR", "No se inserto la pieza");
                            } else {
                                Log.e("DATABASE ", "pieza insertada");

                            }
                            Toast.makeText(Comparacion.this, "Correcto", Toast.LENGTH_SHORT).show();

                            //                            }
                            //                            else {
                            //                                if (txtProveTag.getText().length() == 0) {
                            //                                    //  return;
                            //                                }
                            //                                else{
                            //                                    Toast.makeText(Comparacion.this, "El Numero de parte no Corresponde", Toast.LENGTH_SHORT).show();
                            //                                    String writeData = txtProveTag.getText().toString();
                            //                                    byte[] OutData = writeData.getBytes();
                            //                                    //ftDevice.write(OutData, writeData.length());
                            //                                    s.replace(0, s.length(), "");
                            //                                    txtEatonTag.setText("");
                            //                                    txtEatonTag.requestFocus();
                            //                                }

                        } else {


                            if (txtProveTag.getText().length() == 0) {
                                //  return;
                            } else {
                                //Toast.makeText(Comparacion.this, "Los numeros de parte no son correctos", Toast.LENGTH_SHORT).show();
                                String writeData = txtProveTag.getText().toString();
                                byte[] OutData = writeData.getBytes();
                                //ftDevice.write(OutData, writeData.length());
                                s.replace(0, s.length(), "");
                                txtEatonTag.setText("");
                                txtEatonTag.requestFocus();
                                long mresult = myDb.addPieza(s_eatontag,s_provetag,String.valueOf(embarque_id),s_eatontag,"NO PASA");

                                if (mresult == -1) {
                                    Log.e("DATABASE ERROR", "No se inserto la pieza");
                                } else {
                                    Log.e("DATABASE ", "pieza insertada");

                                }


                                StartErrorDialog();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        KillalertDialog();
                                    }
                                }, 2*60*100);
                            }
//                        txtProveTag.setText("");
//                        txtEatonTag.setText("");
//                        isTrue = false;
                            Log.e("WTF", "No son etiketa");
                        }


                    }
//
//                if(!isTrue)
//                {
//                    txtProveTag.setText("");
//                    txtEatonTag.setText("");
//                }
                }catch (Exception e)
                {
                    Toast.makeText(Comparacion.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }



            }

        };



        txtProveTag.addTextChangedListener(textWatcher1);


        txtProveTag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                char key = event.getNumber();
                Toast.makeText(Comparacion.this, String.valueOf(event.getNumber()), Toast.LENGTH_SHORT).show();

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {


                    s_eatontag = txtEatonTag.getText().toString();
                    s_provetag = txtProveTag.getText().toString();
//                    s_provetag = "105550-CL-70,FE2402220092";
//                    s_eatontag = "105550-CL-70  4298420C91";

                    if (!((s_eatontag.isEmpty()) && (s_provetag.isEmpty()))) {
                        if (ValidarTag(s_eatontag, s_provetag)) {
                            // Toast.makeText(Comparacion.this, "sison", Toast.LENGTH_SHORT).show();
                            nsEaton = getSN(s_eatontag);
                            nsProve = getSN(s_provetag);

                            pnEaton = getNP(s_eatontag);
                            pnProve = getNP(s_provetag);
                            contador++;
                            if (contador < CantPZ) {
//                                contador ++;
                                txtContador.setText(contador + "/ " + CantPZ);
                                progressBar.setProgress(contador);
//                                capa +=1;
//                                if ((capa >3 ))
//                                {
//                                    Toast.makeText(Comparacion.this, "Se alcanzo el limite de capas , Finalize el escaneo", Toast.LENGTH_SHORT).show();
//                                    return  false;
//                                }
//                                piezas = 1;
//
                                txtProveTag.setText("");
                                txtEatonTag.setText("");
                                txtProveTag.clearFocus();
                                txtEatonTag.requestFocus();

                            } else {
                                txtContador.setText(contador + "/ " + CantPZ);
                                Toast.makeText(Comparacion.this, "Todas las piezas han sido escaneadas, Termine el escaneo ", Toast.LENGTH_SHORT).show();
                                progressBar.setProgress(contador);
                                txtEatonTag.setEnabled(false);
                                txtProveTag.setEnabled(false);
//                                piezas += 1;
//                                txtPz.setText(String.valueOf(piezas)+"/4");


                            }

                            // Log.v("Piezas", String.valueOf(piezas));

                            long result = myDb.addPieza(nsEaton,
                                    nsProve,
                                    String.valueOf(embarque_id),
                                    pnProve,
                                    "PASA"
                            );

                            txtProveTag.setText("");
                            txtEatonTag.setText("");

                            if (result == -1) {
                                Log.e("DATABASE ERROR", "No se inserto la pieza");
                            } else {
                                // Toast.makeText(Comparacion.this, "CORRECTO", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(Comparacion.this, "Los numeros de parte no son correctos", Toast.LENGTH_SHORT).show();
                            StartErrorDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    KillalertDialog();
                                }
                            }, 5000);
                            txtProveTag.setText("");
                            txtEatonTag.setText("");
//                            txtEatonTag.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    txtEatonTag.requestFocus();
//
//
//                                }
//                            });

                        }
                    } else {
                        Toast.makeText(Comparacion.this, "No se han escanedo las piezas", Toast.LENGTH_SHORT).show();
                        txtEatonTag.requestFocus();

                    }
                    txtEatonTag.post(new Runnable() {
                        @Override
                        public void run() {
                            txtEatonTag.requestFocus();


                        }
                    });
                }



                    return true;
                }


        });



    }

    private boolean ValidarTag(String str1, String str2) {

        String part_number_eaton = "";
        String part_number_prove = "";

        if (str1.isEmpty() || str2.isEmpty())
        {
            return false;
        }

        if (str1.equals(str2))
        {
            return false;
        }else if (str1.contains(",") && str2.contains(","))
        {
            return false;
        }
        else if (str1.contains(" ") && str2.contains(" "))
        {

        }else
        {

            if (str2.contains(","))
            {
                part_number_prove =str2.substring(0,str2.indexOf(","));
                Log.v("Proveedor Tag",part_number_prove);
                part_number_eaton = str1.substring(0,str1.indexOf(" "));
                Log.v("Eaton TAG",part_number_eaton);
            } else if (str1.contains(",")) {
                part_number_prove =str1.substring(0,str1.indexOf(","));
                Log.v("Proveedor Tag",part_number_prove);
                part_number_eaton = str2.substring(0,str2.indexOf(" "));
                Log.v("Eaton TAG",part_number_eaton);
            }else {
                return false;
            }
        }





        return part_number_eaton.equals(part_number_prove);
        /*
        * El metodo de la clase String . Equals , retorna verdadero o falso, por lo que se simplifica el return
        * */
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnfinalizarescaneo)
        {
            startActivity(new Intent(Comparacion.this,MenuActivityApp.class));
            finish();

          //  createReport(embarque_id);
           
        }
    }

    public String getSN (String SN)
    {
        String serial_number = "";

        if (SN.contains(","))
        {
            serial_number =SN.substring(SN.indexOf(",")+1,(SN.length()-1));
            Log.v("Proveedor Tag",SN);

        }else
        {
            serial_number =SN.substring(SN.indexOf(" ")+1,(SN.length()-1));
            Log.v("Proveedor Tag",SN);
        }

        return serial_number;

    }

    public String getNP(String NP)
    {
        String part_numner="";
        if (NP.contains(","))
        {
            part_numner =NP.substring(0,NP.indexOf(","));


        }else
        {
            part_numner =NP.substring(0,NP.indexOf(" "));

        }
        return part_numner;
    }



//   public String  getPart( String np )
//    {
//        String part = "";
//
//        if (np.isEmpty())
//        {
//        return part;
//
//        }
//        else
//        {
//            if (np.contains(","))
//            {
//                part = np.substring(0,np.indexOf(","));
//            }
//            else
//            {
//                part = np.substring(0,np.indexOf(" "));
//            }
//
//        }
//
//
//        return part;
//
//    }



    void  StartErrorDialog ()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Comparacion.this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_wrong_pn,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();
    }
    void KillalertDialog(){
        alertDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setProgress(0);

    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }
}
