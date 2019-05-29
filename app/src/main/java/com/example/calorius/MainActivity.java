package com.example.calorius;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference dbUsuarios;
    private ValueEventListener eventListener;
    String usu="",email,dni,nombre,fotoURL;
    private TextView usuarioText;
    private ImageView foto;
    private SharedPreferences sharedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //************* OBTENER SHARED EMAIL USUARIO LOGUEADO *************
        sharedData = getSharedPreferences("USUARIO", Context.MODE_PRIVATE);
        email = sharedData.getString(("USUARIO"), "");

        //************* PERSONALIZAR MENÚ USUARIO *************
        View headerView = navigationView.getHeaderView(0);
        usuarioText = headerView.findViewById(R.id.textView);
        foto = headerView.findViewById(R.id.imageView);

        //*********** OBTENER DATOS DEL USUARIO **************
        actualizarHeader();

    }

    public void actualizarHeader(){

//        SharedPreferences spf = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
//        navUsername.setText("Hola " + spf.getString("Email",""));

        dbUsuarios = FirebaseDatabase.getInstance().getReference()
                .child("usuario");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot iterador: dataSnapshot.getChildren()) {

                    if(iterador.child("email").getValue().equals(email)){
                        //************ OBTENER DNI **************
                        dni = iterador.child("dni").getValue().toString();
                        //*********** OBTENER FOTO USUARIO **************
                        fotoURL = iterador.child("foto").getValue().toString();
                        Glide.with(MainActivity.this)
                                .load(fotoURL)
                                .into(foto);
                        SharedPreferences.Editor editor = sharedData.edit();
                        editor.putString("DNI", dni);
                        editor.commit();
                        usuarioText.setText(iterador.child("email").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "Error!", databaseError.toException());
            }
        };

        dbUsuarios.addValueEventListener(eventListener);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            // Lo que pasa cuando pulsas en login
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_logout) { //Lo que pasa cuando pulsas en logout
            LogoutFragment logoutFragment = new LogoutFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, logoutFragment).commit();
        } else if (id == R.id.nav_registcalorias) { //Lo que....
            regCalFragment regcalfragment = new regCalFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, regcalfragment).commit();        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
