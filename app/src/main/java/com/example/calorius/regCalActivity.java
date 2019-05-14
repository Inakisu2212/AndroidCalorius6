package com.example.calorius;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

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
    private String correoLog = "a";
    private Spinner dropdownCant;

    //Estos son params que registraremos
    private String nombreAlSel;
    private String fechaAlSel;
    private String tipoComidaSel;
    private String codigoAlSel;
    private String cantidadAlSel;

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
        calendar = (CalendarView) findViewById(R.id.calendarView);
        dropdownCant = (Spinner) findViewById(R.id.spinnerCant);

        //Obtenemos base de datos con alimentos para poder mostrar en spinner
        DatabaseReference dbAlimentos = FirebaseDatabase.getInstance()
                .getReference().child("alimentos");

        dbAlimentos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i<=5; i++){
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
