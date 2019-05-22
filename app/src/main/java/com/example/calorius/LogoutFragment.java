package com.example.calorius;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class LogoutFragment extends Fragment {
    private Button mLogout;
    private FirebaseAuth mAuth;

    public LogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        SharedPreferences preferences = getContext().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        mLogout = (Button) v.findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                //startActivity(new Intent(LogoutActivity.this,LoginActivity.class));
                Vibrator vibrator = (Vibrator) LogoutFragment.this
                        .getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(60);
                Toast.makeText(getActivity(),"Logout realizado",
                        Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getContext()
                        .getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
            }
        });


        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Vibrator vibrator = (Vibrator) LogoutFragment.this
                        .getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(60);
                Toast.makeText(getActivity(),"Sesión cerrada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),LoginActivity.class));

            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)getActivity()).actualizarHeader();
            }

        });

        return v;
    }
}


