package com.szabhun.sgttteam.hangman;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class MenuActivity extends AppCompatActivity {

    //TextView tvdebug2;

    private static final String TAG = "loginfo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Typeface menu_fonts = Typeface.createFromAsset(getAssets(),  "fonts/menu_fonts.ttf");

        //tvdebug2 = findViewById(R.id.tv_debug2);

        Button menu_bt_1 = findViewById(R.id.menu_bt_1);
        Button menu_bt_2 = findViewById(R.id.menu_bt_2);
        Button menu_bt_3 = findViewById(R.id.menu_bt_3);
        Button menu_bt_4 = findViewById(R.id.menu_bt_4);
        Button menu_bt_5 = findViewById(R.id.menu_bt_5);

        menu_bt_1.setBackground(getResources().getDrawable(R.drawable.menu_bt));
        menu_bt_1.setTypeface(menu_fonts);
        menu_bt_1.setTextColor(Color.BLACK);
        menu_bt_1.setTextSize(19);


        menu_bt_2.setBackground(getResources().getDrawable(R.drawable.menu_bt));
        menu_bt_2.setTypeface(menu_fonts);
        menu_bt_2.setTextColor(Color.BLACK);
        menu_bt_2.setTextSize(19);

        menu_bt_3.setBackground(getResources().getDrawable(R.drawable.menu_bt));
        menu_bt_3.setTypeface(menu_fonts);
        menu_bt_3.setTextColor(Color.BLACK);
        menu_bt_3.setTextSize(19);

        menu_bt_4.setBackground(getResources().getDrawable(R.drawable.menu_bt));
        menu_bt_4.setTypeface(menu_fonts);
        menu_bt_4.setTextColor(Color.BLACK);
        menu_bt_4.setTextSize(19);

        menu_bt_5.setBackground(getResources().getDrawable(R.drawable.menu_bt));
        menu_bt_5.setTypeface(menu_fonts);
        menu_bt_5.setTextColor(Color.BLACK);
        menu_bt_5.setTextSize(19);

        LinearLayout menu_background = findViewById(R.id.menu_background);
        menu_background.setBackgroundResource(R.drawable.emptyactivitybackground);

        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String language = globalvariables.getLanguagecode();

        int languagenum = Integer.parseInt(language);

        //tvdebug2.setText(language);

        if (languagenum == 1)
        {
            menu_bt_1.setText(getString(R.string.hu_menu_bt_1_NewGame));
            menu_bt_2.setText(getString(R.string.hu_menu_bt_2_Scores));
            menu_bt_3.setText(getString(R.string.hu_menu_bt_3_Options));
            menu_bt_4.setText(getString(R.string.hu_menu_bt_4_About));
            menu_bt_5.setText(getString(R.string.hu_menu_bt_5_Exit));
        }
        else if (languagenum == 0)
        {
            menu_bt_1.setText(getString(R.string.en_menu_bt_1_NewGame));
            menu_bt_2.setText(getString(R.string.en_menu_bt_2_Scores));
            menu_bt_3.setText(getString(R.string.en_menu_bt_3_Options));
            menu_bt_4.setText(getString(R.string.en_menu_bt_4_About));
            menu_bt_5.setText(getString(R.string.en_menu_bt_5_Exit));

        };

    }


    @Override
    protected void onResume(){
        super.onResume();

        Button menu_bt_1 = findViewById(R.id.menu_bt_1);
        Button menu_bt_2 = findViewById(R.id.menu_bt_2);
        Button menu_bt_3 = findViewById(R.id.menu_bt_3);
        Button menu_bt_4 = findViewById(R.id.menu_bt_4);
        Button menu_bt_5 = findViewById(R.id.menu_bt_5);

        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String language = globalvariables.getLanguagecode();

        int languagenum = Integer.parseInt(language);

        //tvdebug2.setText(language);
        if (languagenum == 1)
        {
            menu_bt_1.setText(getString(R.string.hu_menu_bt_1_NewGame));
            menu_bt_2.setText(getString(R.string.hu_menu_bt_2_Scores));
            menu_bt_3.setText(getString(R.string.hu_menu_bt_3_Options));
            menu_bt_4.setText(getString(R.string.hu_menu_bt_4_About));
            menu_bt_5.setText(getString(R.string.hu_menu_bt_5_Exit));
        }
        else if (languagenum == 0)
        {
            menu_bt_1.setText(getString(R.string.en_menu_bt_1_NewGame));
            menu_bt_2.setText(getString(R.string.en_menu_bt_2_Scores));
            menu_bt_3.setText(getString(R.string.en_menu_bt_3_Options));
            menu_bt_4.setText(getString(R.string.en_menu_bt_4_About));
            menu_bt_5.setText(getString(R.string.en_menu_bt_5_Exit));
        };
    }

    private void checkpreferencesbuttonsound()
    {
        final MediaPlayer mpbutton = MediaPlayer.create(this, R.raw.btpresswav);
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String muteunmute = globalvariables.getOpt_muteunmute();
        int muteunmutenum = Integer.parseInt(muteunmute);
        if (muteunmutenum == 0)
        {
            mpbutton.start();
        }
        else if (muteunmutenum == 1)
        {

        }

    }

    //-----------------------Fullscreen------------------------------//
    @Override
    public void onWindowFocusChanged(boolean focuschange) {
        Log.i(TAG,"Start: Fullscreen.");
        super.onWindowFocusChanged(focuschange);
        View objecthide = getWindow().getDecorView();
        if (focuschange) {
            objecthide.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        }
        Log.i(TAG,"Loaded: Fullscreen.");
    }
    //---------------------------------------------------------------//


    public void newgame (View view) {
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        globalvariables.setDifficulty("normal");
        checkpreferencesbuttonsound();
        Intent open_newgame = new Intent(MenuActivity.this, GameActivity.class);
        startActivity(open_newgame);
    }

    public void scores (View view) {
        checkpreferencesbuttonsound();
        Intent open_scores = new Intent(MenuActivity.this, ScoresActivity.class);
        startActivity(open_scores);
    }

    public void options (View view) {
        checkpreferencesbuttonsound();
        Intent open_options = new Intent(MenuActivity.this, OptionsActivity.class);
        startActivity(open_options);
    }

    public void about (View view) {
        checkpreferencesbuttonsound();
        Intent open_about = new Intent(MenuActivity.this, AboutActivity.class);
        startActivity(open_about);
    }

    public void exit (View view)
    {
        checkpreferencesbuttonsound();
        finish();
        System.exit(0);
    }


}
