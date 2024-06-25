package com.example.cdsofttag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AlertDialog alertDialog;
    TextView txt_numero_empleado;
    MyDatabaseHelper myDb;
    String num_empleado ="";

    Button btn_login;
    String pastMonth = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9);
        }
        myDb = new MyDatabaseHelper(getApplicationContext());


        File file = new File(Environment.getExternalStorageDirectory() ,"Navistar");
        if (!file.exists())
        {
            file.mkdir();
            //Toast.makeText(this, "Folder Creado", Toast.LENGTH_SHORT).show();
        }




        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this, MenuActivityApp.class);
        txt_numero_empleado = findViewById(R.id.txtnumempleado);
        btn_login = findViewById(R.id.btnlogin);
        btn_login.setOnClickListener(this);
        num_empleado = txt_numero_empleado.getText().toString();

        txt_numero_empleado.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    num_empleado = txt_numero_empleado.getText().toString();

                    if (!num_empleado.isEmpty())
                    {
                        if (num_empleado.equals("Admin123")) {

                            bundle.putBoolean("isAdmin", true);

                        }
                        else
                        {

                            bundle.putBoolean("isAdmin", false);

                        }
                        bundle.putString("user",num_empleado);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Complete la información", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;

            }

        });

        DateFormat dateFormatY = new SimpleDateFormat("YYYY");
        DateFormat dateFormatM = new SimpleDateFormat("MM");
        DateFormat dateFormatD = new SimpleDateFormat("dd");

        Date date = new Date();
        String currentY = dateFormatY.format(date);
        String currentM = dateFormatM.format(date);
        String currentD = dateFormatD.format(date);

        if (Integer.parseInt(currentM) >9)
        {
            pastMonth  = String.valueOf(Integer.parseInt(currentM)-1);
        }
        else
        {
            pastMonth  = "0" +String.valueOf(Integer.parseInt(currentM)-1);
        }



        Log.v("current Day" , currentD);
        if (currentD.equals("15"))
        {

            startAlertDialogLoading();
            if (!currentM.equals("01"))
            {
                String lowerDate = currentY + "-" + pastMonth+"-"+currentD;
                String upperDate = currentY + "-" + currentM+"-"+currentD;
                Log.v("Current date",upperDate);
                Log.v("Current pasDate",lowerDate);
                GenerarReporte (lowerDate,upperDate);

            }
            else {
                String lowerDate = String.valueOf(Integer.parseInt(currentY)-1) + "-" + pastMonth+"-"+currentD;
                String upperDate = currentY + "-" + currentM+"-"+currentD;
            }
        }




//


    }

    private int GenerarReporte(String lowerDate, String upperDate) {


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        int row = 2;
        int column = 2;
        Row row1 = sheet.createRow(row);
        Row headrow= sheet.createRow(1);
        headrow.setHeight((short) 500);
        CellStyle cellStyle0 =  headrow.getSheet().getWorkbook().createCellStyle();
        cellStyle0.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        cellStyle0.setVerticalAlignment(VerticalAlignment.CENTER);
//        cellStyle0.setBorderLeft(BorderStyle.THICK);
//        cellStyle0.setBorderBottom(BorderStyle.THICK);
//        cellStyle0.setBorderRight(BorderStyle.THICK);
//        cellStyle0.setBorderTop(BorderStyle.THICK);
        cellStyle0.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle0.setFillPattern(FillPatternType.SOLID_FOREGROUND);








        Cell cellhead= headrow.createCell(column);
        cellhead.setCellValue("REPORTE GENERAL DE PIEZAS ESCANEADAS");
        cellhead.setCellStyle(cellStyle0);
        sheet.addMergedRegion(new CellRangeAddress(1,1,column,7));

        row1.setHeight((short) 500);
//        Cell cell0 = row1.createCell(column);
//        column++;
     //   sheet.autoSizeColumn(2);
        CellStyle cellStyle =  row1.getSheet().getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderLeft(BorderStyle.THICK);
        cellStyle.setBorderBottom(BorderStyle.THICK);
        cellStyle.setBorderRight(BorderStyle.THICK);
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


      //  cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());

        Cell cell1 = row1.createCell(column);
        cell1.setCellStyle(cellStyle);
        column++;

        Cell cell2 = row1.createCell(column);
        column++;
        cell2.setCellStyle(cellStyle);
        Cell cell3 = row1.createCell(column);
        column++;

        cell3.setCellStyle(cellStyle);
        Cell cell4 = row1.createCell(column);
        column++;
        cell4.setCellStyle(cellStyle);

        Cell cell5 = row1.createCell(column);
        column++;

        cell5.setCellStyle(cellStyle);
        Cell cell6 = row1.createCell(column);
        cell6.setCellStyle(cellStyle);

//        cell0.setCellValue("Capa");
//
        cell1.setCellValue("Fecha");

        cell2.setCellValue("Hora");

        cell3.setCellValue("Numero de Parte");

        cell4.setCellValue("Numero de \n Serie Proveedor");

        cell5.setCellValue("Numero de Serie Eaton");

        cell6.setCellValue("Igual Numero de parte ");

        column = 2;


        //  Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
        Cursor pz = myDb.readPiezas(lowerDate, upperDate);

        if (pz.getCount() == 0) {
            //Toast.makeText(this, "No se encontraron clientes", Toast.LENGTH_SHORT).show();
            Log.v("FetchingPz ", "Query does not work");
        }
        else{
                Log.v("registros", String.valueOf(pz.getCount()));
                int i = pz.getCount();
                int drow = 3;


                while (pz.moveToNext()) {
                    Row row2 = sheet.createRow(drow++);
                    row2.setHeight((short) 400);
                    CellStyle cellStyle1 = row2.getSheet().getWorkbook().createCellStyle();
                    cellStyle1.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                    cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellStyle1.setBorderLeft(BorderStyle.THIN);
                    cellStyle1.setBorderBottom(BorderStyle.THIN);
                    cellStyle1.setBorderRight(BorderStyle.THIN);
                    cellStyle1.setBorderTop(BorderStyle.THIN);
                    cellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);







//                    Cell cell01 = row2.createCell(column);
//                    column++;
                    Cell cell02 = row2.createCell(column);
                    cell02.setCellStyle(cellStyle1);
                    column++;
                    Cell cell03 = row2.createCell(column);
                    cell03.setCellStyle(cellStyle1);
                    column++;
                    Cell cell04 = row2.createCell(column);
                    cell04.setCellStyle(cellStyle1);
                    column++;
                    Cell cell05 = row2.createCell(column);
                    cell05.setCellStyle(cellStyle1);
                    column++;
                    Cell cell06 = row2.createCell(column);
                    cell06.setCellStyle(cellStyle1);
                    column++;
                    Cell cell07 = row2.createCell(column);
                    cell07.setCellStyle(cellStyle1);
//                    cell01.setCellValue(pz.getString(0));
                    cell02.setCellValue(pz.getString(0));
                    cell03.setCellValue(pz.getString(1));
                    cell04.setCellValue(pz.getString(2));
                    cell05.setCellValue(pz.getString(3));
                    cell06.setCellValue(pz.getString(4));
                    cell07.setCellValue(pz.getString(5));


                    Log.d("BD", "createReport: " + pz.getString(0)
                                    + pz.getString(1)
                                    + pz.getString(2)
                                    + pz.getString(3)
                                    + pz.getString(4)
//                            + pz.getString(5)
//                            + pz.getString(6)
                    );

                    column = 2;
                    Log.d("TAG", String.valueOf(drow));
                }

                    sheet.setColumnWidth(2, 4000);
                    sheet.setColumnWidth(4, 4000);
                    sheet.setColumnWidth(5, 7000);
                    sheet.setColumnWidth(6, 7000);
                    sheet.setColumnWidth(7, 6000);
            }

            try {


                File file = new File(Environment.getExternalStorageDirectory(), "/Navistar/" + getFileNme() + "_NAVISTAR" + ".xlsx");

                FileOutputStream fileOutputStream = new FileOutputStream(file);

                workbook.write(fileOutputStream);
                KillalertDialog();
                Toast.makeText(this, "Archivo Creado en la carpeta Navistar", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Comparacion.this,MenuActivityApp.class));
            } catch (Exception e) {
                Log.e("Error creando arcivhjiv", e.getMessage().toString());
            }
            Log.v("Archivo", "ArchivoCreado");


            return 0;

    }












    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this, MenuActivityApp.class);
        if (v.getId() == R.id.btnlogin)
        {
            num_empleado = txt_numero_empleado.getText().toString();

            if (!num_empleado.isEmpty())
            {
                if (num_empleado.equals("Admin123")) {

                    bundle.putBoolean("isAdmin", true);

                }
                else
                {

                    bundle.putBoolean("isAdmin", false);

                }
                bundle.putString("user",num_empleado);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "Complete la información", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void  startAlertDialogLoading ()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();
    }
    void KillalertDialog(){
        alertDialog.dismiss();
    }



    private  String getFileNme (){
        String pastMont = "";
        String currentMonthName = "";
        String prevMonthName ="";
        DateFormat dateFormatY = new SimpleDateFormat("yyyy");
        DateFormat dateFormatM = new SimpleDateFormat("MM");
        Date date = new Date();
        if (Integer.parseInt(dateFormatM.format(date)) == 1){
            pastMont = "12";
        }else if (Integer.parseInt(dateFormatM.format(date)) <= 9)
        {
            pastMont = "0" +String.valueOf(Integer.parseInt(dateFormatM.format(date))-1);
        }
        else
        {
            pastMont = String.valueOf(Integer.parseInt(dateFormatM.format(date))-1);
        }

        prevMonthName=getMonthName(pastMonth);
        currentMonthName = getMonthName(dateFormatM.format(date));


        return prevMonthName+"_"+currentMonthName+"_"+dateFormatY.format(date);
    }

    private  String getMonthName(String numMonth)
    {
        switch (numMonth)
        {
            case "01":
                return  "ENE";
            case "02":
                return   "FEB";

            case "03":
                return   "MAR";

            case "04":
                return  "ABR";

            case "05":
                return  "MAY";

            case "06":
                return   "JUN";

            case "07":
                return  "JUL";

            case "08":
                return   "AGO";

            case "09":
                return  "SEP";

            case "10":
                return   "OCT";

            case "11":
                return   "NOV";

            case "12":
                return   "DIC";

            default:
                return "";
        }
    }


}