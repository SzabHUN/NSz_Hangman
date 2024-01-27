package com.szabhun.sgttteam.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class AboutActivity extends AppCompatActivity {


    TextView credits,description,sztiltle,sptiltle,ltiltle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        credits = findViewById(R.id.TV_AB_CRDS);
        description = findViewById(R.id.TV_AB_Desc);
        sztiltle = findViewById(R.id.TV_Sz_Tiltle);
        sptiltle = findViewById(R.id.TV_Sp_Tiltle);
        ltiltle = findViewById(R.id.TV_L_Tiltle);

    }
}
