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


    
    ImageView hangman;      
    LinearLayout MLayout;   
    TextView scoreview;


    @Override
    public Context createDisplayContext(Display display) {
        return super.createDisplayContext(display);
    }


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.i(TAG,"Start: onCreate.");



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

    //-----------------------Vibrator service and parameters--------------------------------------//
    
    //--------------------------------------------------------------------------------------------//
    //----------------------------------------//
    
    //----------------------------------------//

    

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


