package com.example.calorius;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_cal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dropdownAl =(Spinner) findViewById(R.id.spinnerAlimentos);
        dropdownCom = (Spinner) findViewById(R.id.spinnerComidaArray);
        calendar = (CalendarView) findViewById(R.id.calendarView);
        dropdownCant = (Spinner) findViewById(R.id.spinnerCant);

        //Obtenemos base de datos con alimentos para poder mostrar en spinner
        DatabaseReference dbAlimentos = FirebaseDatabase.getInstance()
                .getReference().child("alimentos");

        //dbAlimentos.

    }

}
