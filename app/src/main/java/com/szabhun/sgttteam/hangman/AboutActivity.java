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
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String language = globalvariables.getLanguagecode();
        int languagenum = Integer.parseInt(language);
        if (languagenum == 1)
        {
            credits.setText(R.string.hu_Crds);
            description.setText(R.string.hu_AB_Desc);
            sztiltle.setText(R.string.hu_Sz_t);
            sptiltle.setText(R.string.hu_Sp_t);
            ltiltle.setText(R.string.hu_L_t);
        }
        else if (languagenum == 0)
        {
            credits.setText(R.string.Crds);
            description.setText(R.string.AB_Desc);
            sztiltle.setText(R.string.Sz_t);
            sptiltle.setText(R.string.Sp_t);
            ltiltle.setText(R.string.L_t);
        }
    }
}
