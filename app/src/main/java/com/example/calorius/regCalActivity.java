package com.example.calorius;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class regCalActivity extends AppCompatActivity {

    private Spinner dropdownAl;
    private Spinner dropdownCom;
    private CalendarView calendar;
    private String fechaSeleccionada;
    private String DNILogueado = "0000";
    private EditText numCantidad;
    private Button btnReg;

    private static final String LOGTAG = "Error reg. calorias: ";

    //Estos son params que registraremos
    private String nombreAlSel;
    private String fechaAlSel;
    private String tipoComidaSel;
    private String codigoAlSel;
   // private String cantidadAlSel;

    private Caloria cal;

    private ArrayList<String> alimentos  = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_cal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dropdownAl =(Spinner) findViewById(R.id.spinnerAl);
        dropdownCom = (Spinner) findViewById(R.id.spinnerTipo);
        numCantidad = (EditText) findViewById(R.id.editNumcantidad);

        Button btnReg = (Button) findViewById(R.id.botonReg);

        //Obtenemos fecha seleccionada del calendario
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                month = month+1;
                fechaSeleccionada = year+"-"+month+"-"+dayOfMonth;
            }
        });

        //Tipos de comida para meter en spinnerTipo
        String[] spinnerComAr = new String[3];
        spinnerComAr[0]="Desayuno";
        spinnerComAr[1]="Almuerzo";
        spinnerComAr[2]="Cena";
        //Llenamos el spinner con los nombres de tipos de comida
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(regCalActivity.this,
                android.R.layout.simple_spinner_dropdown_item, spinnerComAr);
        dropdownCom.setAdapter(adapter2);

        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Obtenemos el tipo de comida que se ha seleccionado
                if(dropdownCom.getSelectedItemPosition()==0){
                    tipoComidaSel = "D";
                }else if(dropdownCom.getSelectedItemPosition()==1) {
                    tipoComidaSel = "A";
                }else if(dropdownCom.getSelectedItemPosition()==2){
                    tipoComidaSel = "C";
                }

                //Obtener el id del alimento que se ha seleccionado
                int idAlSeleccionado = dropdownAl.getSelectedItemPosition(); //añado +1
                codigoAlSel = Integer.toString(idAlSeleccionado+1);

                //Obtener número de alimentos seleccionado
                String numAlimentos = numCantidad.getText().toString();

                //Introducimos el registro de calorias
                DatabaseReference calRef =  FirebaseDatabase.getInstance()
                        .getReference().child("calorias");
                Caloria cal = new Caloria();
                cal.setCantidad(numAlimentos);
                cal.setFecha(fechaSeleccionada);
                cal.setTipoAlimento(tipoComidaSel);
                cal.setUsuario(DNILogueado);

                calRef.child(fechaSeleccionada+tipoComidaSel+codigoAlSel+DNILogueado)
                        .setValue(cal, new DatabaseReference.CompletionListener(){
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if(error == null) {
                            Log.i(LOGTAG, "Operación OK");
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(60);
                            Toast.makeText(regCalActivity.this, "Registro realizado", Toast.LENGTH_SHORT).show();
                            System.out.println("---> Reg Cal OK: Verificar en BD!");
                        }else {
                            Log.e(LOGTAG, "Error: " + error.getMessage());

                            Vibrator vibrator2 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            Toast.makeText(regCalActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                            long[] pattern = {0, 60, 50, 60, 50, 60};
                            vibrator2.vibrate(pattern, -1);
                            System.out.println("----->Fallo de introducirCalorias: "); //info del error
                        }
                    }
                });

            }
        });

        //Obtener la fecha seleccionada del calendario
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                month = month+1;
                fechaSeleccionada = year+"-"+month+"-"+dayOfMonth;
            }
        });

        //Obtenemos base de datos con alimentos para poder mostrar en spinner
        DatabaseReference dbAlimentos = FirebaseDatabase.getInstance()
                .getReference().child("alimentos");

        dbAlimentos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 1; i<=5; i++){
                    alimentos.add(dataSnapshot.child(""+i+"").child("nombre").getValue().toString());
                }

                adapter = new ArrayAdapter(regCalActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, alimentos);
                dropdownAl.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
