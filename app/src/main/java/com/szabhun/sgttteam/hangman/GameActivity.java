package com.szabhun.sgttteam.hangman;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.lang.UProperty;
import android.media.MediaPlayer;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Method;
import java.util.Random;
import java.util.Set;

import static android.content.ContentValues.TAG;


@SuppressWarnings("ALL")
public class GameActivity extends AppCompatActivity {


     //there are 26 letters in the english alphabet
    //there will be 7 parts to the Hanged Man

    //Button la,lb,lc,ld,le,lf,lg,lh,li,lj,lk,ll,lm,ln,lo,lp,lq,lr,ls,lt,lu,lv,lw,lx,ly,lz;
    //ImageView l00,l01,l02,l03,l04,l05,l06,l07,l10,l11,l12,l13,l14,l15,l16,l17,l20,l21,l22,l23,l24,l25,l26,l27,hangman;
    ImageView hangman;      // The Image of the hangman in the centre
    LinearLayout MLayout;   // Master Layout - optimization so the program doesn't have a pre-determent background
    TextView scoreview;

    public Button[] buttons = new Button[26];           // variable to the 26 individual Buttons so its more dynamic
    public ImageView[][] ltrs = new ImageView[3][8];    /*
                                                        variable to the 2 rows of 8 individual ImageViews that shows the progress of the game 
                                                        - more dynamic - easier to use
                                                        */

    public static String abcb = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // full ABC in caps
    public static String abcs = "abcdefghijklmnopqrstuvwxyz"; // full ABC without caps

    private static final String TAG = "loginfo";

    public String guessThisWord = ""; // the word that the user needs to guess will be imported into this variable

    public Byte streak = 0;

    public String diff = "SzabHUN";
    public int score = 0;
    public String scoreTable;

    Random rnd = new Random();

    /*
    GlobalVariables globalvariables=(GlobalVariables)getApplication();
    String language = globalvariables.getLanguagecode();

    public long getScore() {
        return score;
    }
    */
                                                    


    @Override
    public Context createDisplayContext(Display display) {
        return super.createDisplayContext(display);
    }

/*
     * cheat sheet
     * new ResetAll().run(); → same thread
     * new Thread(ResetRun).start(); → new
     */


    //create reset class
    ResetAll ResetRun = new ResetAll();

    //create Global Variables fetching class
    GetGlobal GlobalRun = new GetGlobal();

    //create class to pair front-end to back-end
    PairUI PairRun = new PairUI();

    //
    NewWord NewRun = new NewWord();

    //
    ClearScreen ClearRun = new ClearScreen();


    public int points = 0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.i(TAG,"Start: onCreate.");

        checkpreferencesbackgroundsound();

        //getGlobalz(); // getting the global variables

        scoreview=findViewById(R.id.ScoreView);
        scoreview.setText(Long.toString(score));
        MLayout = findViewById(R.id.Game_MLayout);
        hangman = findViewById(R.id.Hangman);
        MLayout.setBackgroundResource(R.drawable.crumpedpaperbackgroundflipped);
        //---------------------------//

        new Thread(GlobalRun).start();
        //---------------------------//
        Log.i(TAG,"Start: Pair UI objects.");
        //PairUIElements();
        new PairUI().run();
        Log.i(TAG,"Finished: Pair UI objects.");
        //---------------------------//
        //---------------------------//
        //new Thread(ResetRun).start();
        //---------------------------//
        //SelectAWord();
        //---------------------------//
        //MarkingTheSpaceForTheWord();
        //---------------------------//
        new ResetAll().run();
        new NewWord().run();
        //---------------------------//
        if (diff == "SzabHUN")
        {
            Toast.makeText(this, "Difficulty is Incorrect, '" +diff+"' is not in the database, and I will draw this line out so its rather visible in debugging",15).show();
            Log.i(TAG, "Difficulty is Incorrect, '"+diff+"' is not in the database, and I will draw this line out so its rather visible in debugging, resetting the value to 'normal'.");
            diff="normal";
        }
        //---------------------------//
        //---------------------------//




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
    //public GlobalVariables globalvariables=(GlobalVariables)getApplication();
    /*
    public void getGlobalz() {
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String tmp1 = globalvariables.getOpt_difficulty();
        //diff = tmp;
        switch (tmp1)
        {
            case "1":
                diff = "easy";
                break;

            case "2":
                diff = "normal";
                break;

            case "3":
                diff = "hard";
                break;

            default:
                Log.i(TAG,"!!!no difficulty was selected - setting it to normal!!!");
                diff = "normal";
                break;
        }
        GetGlobal getGlobal = new GetGlobal(this);
        diff = getGlobal.Diff().toString();
        score = getGlobal.Score();

        try {
            score = Integer.parseInt(globalvariables.getScore());
        }
        catch (NumberFormatException e)
        {
            score = 0;
        }

        try {
            scoreTable = globalvariables.getScoreTable();
        }
        catch (Exception e)
        {
            scoreTable = "0;0;0;0;0;0;0;0;0";
        }
    }
    */
    /*
    public void PairUIElements ()
    {
        //---------------------------//--The-ImageViews--\\
        Log.i(TAG,"Start: Pair UI objects - ImageView.");
        for (int i = 0 ; i < 3 ; i++)
            for (int q = 0 ; q < 8 ; q++)
            {
                String ImgvID = "LetterView" + i + q;                                       //String value to re-create the name of the objects made in the XML
                int resID = getResources().getIdentifier(ImgvID, "id", getPackageName());   //get the Resource ID using the ID created beforehand
                ltrs[i][q] = findViewById(resID);                                           //assigns a lrts[][] variable with a item in the XML
            }
        Log.i(TAG,"Finished: Pair UI objects - ImageView.");
        //---------------------------//----The-Buttons----\\
        Log.i(TAG,"Start: Pair UI objects - Button.");
        for(int i = 0 ; i < 26 ; i++)
        {
            String ButtonID = "BT_" + abcb.toCharArray()[i];                                //String value to re-create the name of the objects made in the XML
            int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());     //get the Resource ID using the ID created beforehand
            buttons[i] = findViewById(resID);                                               //assigns a buttons[][] variable with a item in the XML
        }
        Log.i(TAG,"Finished: Pair UI objects - Button.");

    }

     */

     public void NewWord (View view)
    {

        //---------------------------//
        Log.i(TAG,"Start: Full reset.");
        Reset();
        Log.i(TAG,"Finished: Full reset.");
        //---------------------------//
        SelectAWord();
        //---------------------------//
        MarkingTheSpaceForTheWord();
        //---------------------------//
        points = 0;
        lives = 7;

    }

    public void ClearScreen (View view)
    {
        //---------------------------//
        Log.i(TAG,"Start: Full reset.");
        Reset();
        Log.i(TAG,"Finished: Full reset.");
        //---------------------------//
        MarkingTheSpaceForTheWord();
        //---------------------------//
        points = 0;
        lives = 7;
        //new Thread(ClearRun).start();
    }

    

    //----------------------------------------//
    


    

    //----------------------------------------//
    
    //----------------------------------------//

    public int lives = 7;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------


    


    

    //----------------------------------------------------------------------------------------------

    

    public class GetGlobal implements Runnable {

        @Override
        public void run() {
            Log.i(TAG,"asd");
            GlobalVariables globalvariables=(GlobalVariables)getApplication();
            String tmp = globalvariables.getOpt_difficulty();
            //diff = tmp;
            switch (tmp)
            {
                case "1":
                    diff = "easy";
                    break;

                case "2":
                    diff = "normal";
                    break;

                case "3":
                    diff = "hard";
                    break;

                default:
                    Log.i(TAG,"!!!no difficulty was selected - setting it to normal!!!");
                    diff = "normal";
                    break;
            }
            try {
                score = Integer.parseInt(globalvariables.getScore());
            }
            catch (NumberFormatException e)
            {
                score = 0;
            }

            try {
                scoreTable = globalvariables.getScoreTable();
            }
            catch (Exception e)
            {
                scoreTable = "0;0;0;0;0;0;0;0;0";
            }
        }
    }

    public class PairUI implements Runnable {
        @Override
        public void run() {
            //---------------------------//--The-ImageViews--\\
            Log.i(TAG,"Start: Pair UI objects - ImageView.");
            for (int i = 0 ; i < 3 ; i++)
                for (int q = 0 ; q < 8 ; q++)
                {
                    String ImgvID = "LetterView" + i + q;                                       //String value to re-create the name of the objects made in the XML
                    int resID = getResources().getIdentifier(ImgvID, "id", getPackageName());   //get the Resource ID using the ID created beforehand
                    ltrs[i][q] = findViewById(resID);                                           //assigns a lrts[][] variable with a item in the XML
                }
            Log.i(TAG,"Finished: Pair UI objects - ImageView.");
            //---------------------------//----The-Buttons----\\
            Log.i(TAG,"Start: Pair UI objects - Button.");
            for(int i = 0 ; i < 26 ; i++)
            {
                String ButtonID = "BT_" + abcb.toCharArray()[i];                                //String value to re-create the name of the objects made in the XML
                int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());     //get the Resource ID using the ID created beforehand
                buttons[i] = findViewById(resID);                                               //assigns a buttons[][] variable with a item in the XML
            }
            Log.i(TAG,"Finished: Pair UI objects - Button.");

        }
    }

    public class NewWord implements Runnable {
        @Override
        public void run() {
            //---------------------------//
            Log.i(TAG,"Start: Full reset.");
            //new ResetAll().run(); // reset needs to be seperate
            Reset();
            Log.i(TAG,"Finished: Full reset.");
            //---------------------------//
            SelectAWord();
            //---------------------------//
            MarkingTheSpaceForTheWord();
            //---------------------------//
            points = 0;
            lives = 7;

        }
    }

    public class ClearScreen implements Runnable {
        @Override
        public void run() {
            //---------------------------//
            Log.i(TAG,"Start: Full reset.");
            //Reset();
            new ResetAll().run();
            Log.i(TAG,"Finished: Full reset.");
            //---------------------------//
            MarkingTheSpaceForTheWord();
            //---------------------------//
            points = 0;
            lives = 7;
        }
    }


}


