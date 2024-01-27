package com.szabhun.sgttteam.hangman;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {


    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    private static final String TAG = "loginfo";

    TextView tvdifchs, tvdifeasy, tvdifnormal, tvdifhard, maintv,tvflgeng,tvflghun,tvsounds;
    Button btdifeasy, btdifnormal, btdifhard,  btflghun, btflgeng, btsoundmute, btsoundunmute, btsoundmuteuni;

    public boolean muted = false; // local var for easier testing during runtime



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();


        btdifeasy = findViewById(R.id.BT_OPT_DIF_EASY);
        btdifnormal = findViewById(R.id.BT_OPT_DIF_NORMAL);
        btdifhard = findViewById(R.id.BT_OPT_DIF_HARD);
        tvdifchs = findViewById(R.id.TV_OPT_CHNG_DIF);
        tvdifeasy = findViewById(R.id.TV_OPT_DIFTXT_EASY);
        tvdifnormal = findViewById(R.id.TV_OPT_DIFTXT_NORMAL);
        tvdifhard = findViewById(R.id.TV_OPT_DIFTXT_HARD);

        maintv = findViewById(R.id.OPT_TV_CHNG_LNG);
        tvflgeng = findViewById(R.id.TV_OPT_FLGTXT_ENG);
        tvflghun = findViewById(R.id.TV_OPT_FLGTXT_HUN);
        btflghun = findViewById(R.id.BT_OPT_FLG_HUN);
        btflgeng = findViewById(R.id.BT_OPT_FLG_ENG);

        tvsounds = findViewById(R.id.OPT_TV_SOUNDS);
        btsoundmute = findViewById(R.id.BT_OPT_SOUND_MUTE);
        btsoundunmute = findViewById(R.id.BT_OPT_SOUND_UNMUTE);

        btsoundmuteuni = findViewById(R.id.BT_OPT_SOUND_UNIVERSAL); // the new button


        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String language = globalvariables.getLanguagecode();
        String muteunmute = globalvariables.getOpt_muteunmute();
        String opt_difficulty = globalvariables.getOpt_difficulty();

        int languagenum = Integer.parseInt(language);
        int muteunmutenum = Integer.parseInt(muteunmute);
        int opt_difficultynum = Integer.parseInt(opt_difficulty);


        if (muteunmutenum == 1) // I am taking this is the MUTED version
        {
            btsoundunmute.getBackground().setAlpha(128); // sound - inactive
            btsoundmute.getBackground().setAlpha(255);   // mute - active

            muted = true;
        }
        else if (muteunmutenum == 0) // I am taking this is the UNMUTED version
        {
            btsoundunmute.getBackground().setAlpha(255); // sound - active
            btsoundmute.getBackground().setAlpha(128);   // mute - inactive

            muted = false;
        }

        if (opt_difficultynum == 1)
        {
            btdifeasy.setBackgroundResource(R.drawable.opt_checked);
        }
        else if (opt_difficultynum == 2)
        {
            btdifnormal.setBackgroundResource(R.drawable.opt_checked);
        }
        else if (opt_difficultynum == 3)
        {
            btdifhard.setBackgroundResource(R.drawable.opt_checked);
        }


        if (languagenum == 1)
        {
            tvdifchs.setText(R.string.hu_opt_difficulty_chs);
            tvdifeasy.setText(R.string.hu_opt_difficulty_easy);
            tvdifnormal.setText(R.string.hu_opt_difficulty_normal);
            tvdifhard.setText(R.string.hu_opt_difficulty_hard);
            tvsounds.setText(R.string.hu_opt_sounds);
            maintv.setText(R.string.hu_opt_chs);
            tvflgeng.setText(R.string.hu_opt_flg_eng);
            tvflghun.setText(R.string.hu_opt_flg_hun);
            btflgeng.getBackground().setAlpha(128);
            btflghun.getBackground().setAlpha(255);
        }
        else if (languagenum == 0)
        {
            tvdifchs.setText(R.string.opt_difficulty_chs);
            tvdifeasy.setText(R.string.opt_difficulty_easy);
            tvdifnormal.setText(R.string.opt_difficulty_normal);
            tvdifhard.setText(R.string.opt_difficulty_hard);
            tvsounds.setText(R.string.opt_sounds);
            tvsounds.setText(R.string.opt_sounds);
            maintv.setText(R.string.opt_chs);
            tvflgeng.setText(R.string.opt_flg_eng);
            tvflghun.setText(R.string.opt_flg_hun);
            btflghun.getBackground().setAlpha(128);
            btflgeng.getBackground().setAlpha(255);
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

    private void checkpreferencesbuttonsound()
    {
        final MediaPlayer mpbutton = MediaPlayer.create(this, R.raw.btpresswav);
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String opt_muteunmute = globalvariables.getOpt_muteunmute();
        int opt_muteunmutenum = Integer.parseInt(opt_muteunmute);
        if (opt_muteunmutenum == 0)
        {
            mpbutton.start();
        }
        else if (opt_muteunmutenum == 1)
        {

        }

    }

    public void OnButtonClick (View view)
    {
        GlobalVariables globalvariables=(GlobalVariables)getApplication();

        switch (view.getId()) {
            case R.id.BT_OPT_DIF_EASY:
                Log.i(TAG, "Options - SetDifficulty_Easy pressed");
                checkpreferencesbuttonsound();
                btdifeasy.setBackgroundResource(R.drawable.opt_checked);
                globalvariables.setOpt_difficulty("1");
                mEditor.putString(getString(R.string.opt_difficulty), "0");
                mEditor.commit();
                btdifnormal.setBackgroundResource(R.drawable.opt_checkbox);
                btdifhard.setBackgroundResource(R.drawable.opt_checkbox);
                Log.i(TAG, "Options - SetDifficulty_Easy successfully set difficulty to Easy");
                break;
            case R.id.BT_OPT_DIF_NORMAL:
                Log.i(TAG, "Options - SetDifficulty_Normal pressed");
                checkpreferencesbuttonsound();
                btdifnormal.setBackgroundResource(R.drawable.opt_checked);
                globalvariables.setOpt_difficulty("2");
                mEditor.putString(getString(R.string.opt_difficulty), "2");
                mEditor.commit();
                btdifeasy.setBackgroundResource(R.drawable.opt_checkbox);
                btdifhard.setBackgroundResource(R.drawable.opt_checkbox);
                Log.i(TAG, "Options - SetDifficulty_Normal successfully set difficulty to Normal");
                break;
            case R.id.BT_OPT_DIF_HARD:
                Log.i(TAG, "Options - SetDifficulty_Hard pressed");
                checkpreferencesbuttonsound();
                btdifhard.setBackgroundResource(R.drawable.opt_checked);
                globalvariables.setOpt_difficulty("3");
                mEditor.putString(getString(R.string.opt_difficulty), "3");
                mEditor.commit();
                btdifeasy.setBackgroundResource(R.drawable.opt_checkbox);
                btdifnormal.setBackgroundResource(R.drawable.opt_checkbox);
                Log.i(TAG, "Options - SetDifficulty_Hard successfully set difficulty to Hard");
                break;
            case R.id.BT_OPT_FLG_ENG:
                Log.i(TAG, "Options - SetLanguage_ENG pressed");
                checkpreferencesbuttonsound();
                globalvariables.setLanguagecode("0");
                tvdifchs.setText(R.string.opt_difficulty_chs);
                tvdifeasy.setText(R.string.opt_difficulty_easy);
                tvdifnormal.setText(R.string.opt_difficulty_normal);
                tvdifhard.setText(R.string.opt_difficulty_hard);
                tvsounds.setText(R.string.opt_sounds);
                maintv.setText(R.string.opt_chs);
                tvflgeng.setText(R.string.opt_flg_eng);
                tvflghun.setText(R.string.opt_flg_hun);
                mEditor.putString(getString(R.string.opt_language), "0");
                mEditor.commit();
                btflghun.getBackground().setAlpha(128);
                btflgeng.getBackground().setAlpha(255);
                Log.i(TAG, "Options - SetLanguage_ENG pressed");
                break;
            case R.id.BT_OPT_FLG_HUN:
                Log.i(TAG, "Options - SetLanguage_HUN successfully set language to English");
                checkpreferencesbuttonsound();
                globalvariables.setLanguagecode("1");
                tvdifchs.setText(R.string.hu_opt_difficulty_chs);
                tvdifeasy.setText(R.string.hu_opt_difficulty_easy);
                tvdifnormal.setText(R.string.hu_opt_difficulty_normal);
                tvdifhard.setText(R.string.hu_opt_difficulty_hard);
                tvsounds.setText(R.string.hu_opt_sounds);
                maintv.setText(R.string.hu_opt_chs);
                tvflgeng.setText(R.string.hu_opt_flg_eng);
                tvflghun.setText(R.string.hu_opt_flg_hun);
                mEditor.putString(getString(R.string.opt_language), "1");
                mEditor.commit();
                btflgeng.getBackground().setAlpha(128);
                btflghun.getBackground().setAlpha(255);
                Log.i(TAG, "Options - SetLanguage_HUN successfully set language to Hungarian");
                break;
            case R.id.BT_OPT_SOUND_UNMUTE:
                Log.i(TAG, "Options - UnMute button pressed");
                checkpreferencesbuttonsound();
                btsoundunmute.setBackgroundResource(R.drawable.unmute);
                globalvariables.setOpt_muteunmute("0");
                mEditor.putString(getString(R.string.opt_muteunmute), "0");
                mEditor.commit();
                btsoundmute.getBackground().setAlpha(128);
                btsoundunmute.getBackground().setAlpha(255);
                Log.i(TAG, "Options - UnMute button Unmuted the app");
                break;
            case R.id.BT_OPT_SOUND_MUTE:
                Log.i(TAG, "Options - Mute button pressed");
                checkpreferencesbuttonsound();
                btsoundmute.setBackgroundResource(R.drawable.mute);
                globalvariables.setOpt_muteunmute("1");
                mEditor.putString(getString(R.string.opt_muteunmute), "1");
                mEditor.commit();
                btsoundunmute.getBackground().setAlpha(128);
                btsoundmute.getBackground().setAlpha(255);
                Log.i(TAG, "Options - Mute button successfully Muted the app");
                break;
            case R.id.BT_OPT_SOUND_UNIVERSAL:
                Log.i(TAG, "Options - Universal Mute Toggle  - Started");
                if (muted) // simple copy-paste
                {
                    Log.i(TAG, "Options - Universal Mute Toggle  - Starting to UnMute the app ");

                    checkpreferencesbuttonsound();

                    btsoundunmute.setBackgroundResource(R.drawable.unmute);

                    globalvariables.setOpt_muteunmute("0");

                    mEditor.putString(getString(R.string.opt_muteunmute), "0");
                    mEditor.commit();

                    btsoundmute.getBackground().setAlpha(128);
                    btsoundunmute.getBackground().setAlpha(255);

                    Log.i(TAG, "Options - Universal Mute Toggle  - Finished to UnMute the app ");

                    btsoundmuteuni.setBackgroundResource(R.drawable.unmute); // setting the resource image of the new button
                }
                else
                {
                    Log.i(TAG, "Options - Universal Mute Toggle  - Starting to Mute the app ");

                    checkpreferencesbuttonsound();

                    btsoundmute.setBackgroundResource(R.drawable.mute);

                    globalvariables.setOpt_muteunmute("1");

                    mEditor.putString(getString(R.string.opt_muteunmute), "1");
                    mEditor.commit();

                    btsoundunmute.getBackground().setAlpha(128);
                    btsoundmute.getBackground().setAlpha(255);

                    Log.i(TAG, "Options - Universal Mute Toggle  - Finished to Mute the app");

                    btsoundmuteuni.setBackgroundResource(R.drawable.mute); // setting the resource image of the new button
                }

                muted = !muted; // changing the value of 'muted'

                Log.i(TAG, "Options - Universal Mute Toggle Finished");
                break;

        }
    }
}

