package com.szabhun.sgttteam.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class OpenerActivity extends AppCompatActivity {

    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    private static final String TAG = "loginfo";

    TextView vcode,vname,tvdebug;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opener);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        String read_opt_language = mPreferences.getString(getString(R.string.opt_language), "");
        String read_opt_muteunmute = mPreferences.getString(getString(R.string.opt_muteunmute), "");
        String read_opt_difficulty = mPreferences.getString(getString(R.string.opt_difficulty), "");
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        globalvariables.setLanguagecode(read_opt_language);
        globalvariables.setOpt_muteunmute(read_opt_muteunmute);
        globalvariables.setOpt_difficulty(read_opt_difficulty);

        //----------------------------------------------------------------------------------------//

        globalvariables.setScoreTable("0;0;0;0;0;0;0;0;0"); //TODO: replace this with a read from system memory

        //----------------------------------------------------------------------------------------//

        if (read_opt_language.isEmpty())
        {
            globalvariables.setLanguagecode("0");
        }
        if (read_opt_muteunmute.isEmpty())
        {
            globalvariables.setOpt_muteunmute("0");
        }
        if (read_opt_difficulty.isEmpty())
        {
            globalvariables.setOpt_difficulty("2");
        }


        String opt_language = globalvariables.getLanguagecode();
        String opt_muteunmute = globalvariables.getOpt_muteunmute();

        vcode = findViewById(R.id.verCode);
        vname = findViewById(R.id.verName);

        //tvdebug = findViewById(R.id.tv_debug1);

        String versionName = BuildConfig.VERSION_NAME;
        int versionCode = BuildConfig.VERSION_CODE;

        vcode.setText("Version code: " + versionCode);
        vname.setText("Version name: " + versionName);


        //tvdebug.setText(language);

        //Timer start
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Intent opengame = new Intent(OpenerActivity.this, MenuActivity.class);
                startActivity(opengame);
                finish();
            }
        }, 4000);
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
}



