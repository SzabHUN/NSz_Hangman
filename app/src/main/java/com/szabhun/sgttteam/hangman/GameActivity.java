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
private void touchvibrator () {
    Log.i(TAG, "Start: Vibrator service.");
    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    } else {
        v.vibrate(100);
    }
    Log.i(TAG, "Loaded: Vibrator service.");
}
//--------------------------------------------------------------------------------------------//
//----------------------------------------//
    private void Reset ()
    {
        Log.i(TAG,"Start: Reset buttons pair images.");

        for (int i = 0; i < 26 ; i++)
        {
            int resID = getResources().getIdentifier(Character.toString(abcs.toCharArray()[i]), "drawable", getPackageName()); //get the ResourceID from the abcs to reset the source image
            buttons[i].setBackgroundResource(resID); 
        }
        Log.i(TAG,"Loaded: Reset buttons pair images.");
        //---------------------------//
        Log.i(TAG,"Start: Change buttons to clickable.");

        for (int i = 0 ; i < 26 ; i++)      //go trough all of the buttons
            buttons[i].setClickable(true);  //set current button to be clickable
        Log.i(TAG,"Finished: Change buttons to clickable.");
        //---------------------------//
        Log.i(TAG, "Start: Reset LetterViews pair images - Image Resource.");

        for (int i = 0; i < 3 ; i++)
            for (int q = 0 ; q < 8 ; q++)
            {
                String STRemptyplaceholder = "emptyplaceholder";                                                //get the res name
                int resID = getResources().getIdentifier(STRemptyplaceholder, "drawable", getPackageName());    //set resID using the name
                ltrs[i][q].setImageResource(resID);                                                             //set the image to an empty one
            }
        Log.i(TAG,"Finished: Reset LetterViews pair images - Image Resource.");
        //---------------------------//
        Log.i(TAG, "Start: Reset LetterViews pair images - Background Resource.");

        for (int i = 0; i < 3 ; i++)
            for (int q = 0 ; q < 8 ; q++)
            {
                String STRemptyplaceholder = "emptyplaceholder";
                int resID = getResources().getIdentifier(STRemptyplaceholder, "drawable", getPackageName());
                ltrs[i][q].setBackgroundResource(resID);
            }
        Log.i(TAG,"Finished: Reset LetterViews pair images - Background Resource.");
        //---------------------------//
        Log.i(TAG, "Start: Reset HangMan image.");
        hangman.setImageResource(R.drawable.emptyplaceholder);
        Log.i(TAG,"Finished: Reset HangMan image.");
        //---------------------------//
    }
    //----------------------------------------//

    public void LockAllTheButtons ()
    {
        Log.i(TAG,"Start: Locking all the buttons.");
        for (int i = 0 ; i < 26 ; i++)
            buttons[i].setClickable(false);                 //Locks every button so after the game ended the user cannot use them
        Log.i(TAG,"Finished: Locking all the buttons.");
    }

    //----------------------------------------//
    public void SelectAWord ()
    {

        Log.i(TAG,"Start: selecting the a random word");
        Resources res = getResources();                             //makes it available to read from the string arrays

        String[] words;
        if (diff == "easy")
        {
            words = res.getStringArray(R.array.easy_words_array);
            Log.i(TAG, " Successfully selected the EASY words array");
        }
        else if (diff == "normal")
        {
            words = res.getStringArray(R.array.normal_words_array);
            Log.i(TAG, " Successfully selected the NORMAL words array");
        }
        else if (diff == "hard")
        {
            words = res.getStringArray(R.array.hard_words_array);
            Log.i(TAG, " Successfully selected the HARD words array");
        }
        else
        {
            words = res.getStringArray(R.array.normal_words_array);
            Log.i(TAG, "FAILED WORD SELECTION BY DIFFICULTY - RESULTING TO NORMAL DIFFICULTY - GET THIS FIXED ASAP");
        }

        Log.i(TAG, "Loaded: a total of "+words.length+" words");
        guessThisWord = words[rnd.nextInt(words.length)];
        Log.i(TAG, "The selected word is: " + guessThisWord);
        Log.i(TAG,"Finished: selecting the a random word");

    }
    //----------------------------------------//
    public Boolean letMeGo = false;

    //----------------------------------------//
    public void MarkingTheSpaceForTheWord()
    {
        for (int i = 0; i < 2 ; i++) //stops the process while reset class isn't finished
        {
            if (letMeGo == false)
            {
                i--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i(TAG, "Started: Marking the space for the word to be guessed.");
        if (guessThisWord.length() >= 1)
        {
            if (guessThisWord.toCharArray()[0] != ' ')
            {
                ltrs[0][0].setBackgroundResource(R.drawable.underscore1);
            }
            else
            {
                points++;
            }
            if (guessThisWord.length() >= 2)
            {
                if (guessThisWord.toCharArray()[1] != ' ')
                {
                    ltrs[0][1].setBackgroundResource(R.drawable.underscore2);
                }
                else
                {
                    points++;
                }
                if (guessThisWord.length() >= 3)
                {
                    if (guessThisWord.toCharArray()[2] != ' ')
                    {
                        ltrs[0][2].setBackgroundResource(R.drawable.underscore3);
                    }
                    else
                    {
                        points++;
                    }
                    if (guessThisWord.length() >= 4)
                    {
                        if (guessThisWord.toCharArray()[3] != ' ')
                        {
                            ltrs[0][3].setBackgroundResource(R.drawable.underscore4);
                        }
                        else
                        {
                            points++;
                        }
                        if (guessThisWord.length() >= 5)
                        {
                            if (guessThisWord.toCharArray()[4] != ' ')
                            {
                                ltrs[0][4].setBackgroundResource(R.drawable.underscore5);
                            }
                            else
                            {
                                points++;
                            }
                            if (guessThisWord.length() >= 6)
                            {
                                if (guessThisWord.toCharArray()[5] != ' ')
                                {
                                    ltrs[0][5].setBackgroundResource(R.drawable.underscore6);
                                }
                                else
                                {
                                    points++;
                                }
                                if (guessThisWord.length() >= 7)
                                {
                                    if (guessThisWord.toCharArray()[6] != ' ')
                                    {
                                        ltrs[0][6].setBackgroundResource(R.drawable.underscore7);
                                    }
                                    else
                                    {
                                        points++;
                                    }
                                    if (guessThisWord.length() >= 8)
                                    {
                                        if (guessThisWord.toCharArray()[7] != ' ')
                                        {
                                            ltrs[0][7].setBackgroundResource(R.drawable.underscore8);
                                        }
                                        else
                                        {
                                            points++;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.i(TAG, "Finished: Marking the space for the word to be guessed.");
    }
    //----------------------------------------//

    private void checkpreferencesbackgroundsound()
    {
        final MediaPlayer mpbackground = MediaPlayer.create(this, R.raw.gamebackgroundmusic);
        GlobalVariables globalvariables=(GlobalVariables)getApplication();
        String muteunmute = globalvariables.getOpt_muteunmute();
        int muteunmutenum = Integer.parseInt(muteunmute);
        if (muteunmutenum == 0)
        {
            mpbackground.start();
        }
        else if (muteunmutenum == 1)
        {

        }

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
    //----//
    
    public void AnyButtonIsPressed (View view)
    {


        Log.i(TAG, "Start: ButtonOnclick");
        int theIndexOfTheCurrentButton;



        switch(view.getId()) //--------------------deciding-the-button-number--------------------\\
        {                   //----each-button-gives-a-value-that-is-an-index-of-the-ABC-string----\\
            case R.id.BT_A:
                theIndexOfTheCurrentButton = 0;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_B:
                theIndexOfTheCurrentButton = 1;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_C:
                theIndexOfTheCurrentButton = 2;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_D:
                theIndexOfTheCurrentButton = 3;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_E:
                theIndexOfTheCurrentButton = 4;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_F:
                theIndexOfTheCurrentButton = 5;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_G:
                theIndexOfTheCurrentButton = 6;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_H:
                theIndexOfTheCurrentButton = 7;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_I:
                theIndexOfTheCurrentButton = 8;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_J:
                theIndexOfTheCurrentButton = 9;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_K:
                theIndexOfTheCurrentButton = 10;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_L:
                theIndexOfTheCurrentButton = 11;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_M:
                theIndexOfTheCurrentButton = 12;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_N:
                theIndexOfTheCurrentButton = 13;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_O:
                theIndexOfTheCurrentButton = 14;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_P:
                theIndexOfTheCurrentButton = 15;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_Q:
                theIndexOfTheCurrentButton = 16;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_R:
                theIndexOfTheCurrentButton = 17;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_S:
                theIndexOfTheCurrentButton = 18;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_T:
                theIndexOfTheCurrentButton = 19;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_U:
                theIndexOfTheCurrentButton = 20;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_V:
                theIndexOfTheCurrentButton = 21;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_W:
                theIndexOfTheCurrentButton = 22;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_X:
                theIndexOfTheCurrentButton = 23;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_Y:
                theIndexOfTheCurrentButton = 24;
                checkpreferencesbuttonsound();
                break;
            case R.id.BT_Z:
                theIndexOfTheCurrentButton = 25;
                checkpreferencesbuttonsound();
                break;
            default:
                theIndexOfTheCurrentButton = 0;
                checkpreferencesbuttonsound();
                break;
        }//------------------------deciding the button number-------\\


        String currLtr = String.valueOf(abcb.toCharArray()[theIndexOfTheCurrentButton]); // get current letter

        Log.i(TAG,"Started: Pressed '" + currLtr + "' button.");
        String textureID = abcs.toCharArray()[theIndexOfTheCurrentButton] + "c";
        int resID = getResources().getIdentifier(textureID, "drawable", getPackageName());
        buttons[theIndexOfTheCurrentButton].setBackgroundResource(resID);
        buttons[theIndexOfTheCurrentButton].setClickable(false);
        touchvibrator();
        CheckTheUserInput(currLtr); // send data to the checking void  // <------------------------------ here
        Log.i(TAG,"Finished: Pressed '" + currLtr + "' button.");
        
        Log.i(TAG, "Finish: ButtonOnclick");

    }
    //----//
    
    public void CheckTheUserInput (String userInput)
    {


        Log.i(TAG, "Starting: checking the button press");
        if (lives != 0 && points != guessThisWord.length())
        {
            if (guessThisWord.contains(userInput))
            {
                for (int i = 0; i < guessThisWord.length(); i++)
                {
                    if (guessThisWord.toCharArray()[i] == userInput.toCharArray()[0]) // "userinput.toCharArray" needed a Char variable, this was the easiest
                    {
                        points++;
                        int resID = getResources().getIdentifier(userInput.toLowerCase(), "drawable",getPackageName()); //get resources and set output letter resource
                        ltrs[0][i].setImageResource(resID);
                        switch (diff)
                        {
                            case"easy":
                                Log.i(TAG, "Res:easy");
                                score = score + 15;
                                break;
                            case"normal":
                                Log.i(TAG, "Res:normal");
                                score = score + 25;
                                break;
                            case"hard":
                                Log.i(TAG, "Res:hard");
                                score = score + 40;
                                break;
                            default:
                                Log.i(TAG, "Res:wtf-part-win-----------------------WTF_HELP---");
                                //Toast.makeText(this, "WTF - win", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }

                //score += 15;
                /* moved it into the for loop just above
                switch (diff)
                {
                    case"easy":
                        Log.i(TAG, "Res:easy");
                        score = score + 15;
                        break;
                    case"normal":
                        Log.i(TAG, "Res:normal");
                        score = score + 25;
                        break;
                    case"hard":
                        Log.i(TAG, "Res:hard");
                        score = score + 40;
                        break;
                    default:
                        Log.i(TAG, "Res:wtf-part-win-----------------------WTF_HELP---");
                        //Toast.makeText(this, "WTF - win", Toast.LENGTH_LONG).show();
                        break;
                }
                */
                GlobalVariables globalvariables=(GlobalVariables)getApplication();

                globalvariables.setScore(Long.toString(score));

                scoreview.setText(Long.toString(score));

                if (points == guessThisWord.length()) // VICTORY \\---------------------------------
                {
                    VictoryTxt();

                } // VICTORY \\---------------------------------------------------------------------
                Log.i(TAG, "Finished: right userInput reaction");
            }
            else
            {
                Log.i(TAG, "Starting: wrong userInput reaction");
                int tmp = 8 - lives;
                String temp = "hangman" + tmp;
                int resID = getResources().getIdentifier(temp, "drawable",getPackageName());
                lives--;
                hangman.setImageResource(resID);

                if (lives == 0) // DEFEAT \\--------------------------------------------------------
                {

                    DefeatTxt();

                }  // DEFEAT \\---------------------------------------------------------------------

                Log.i(TAG, "Finished: wrong userInput reaction");
            }
        }
        Log.i(TAG, "Finished: checking the button press");

    }
    //----//
    public void VictoryTxt ()
    {

        ltrs[2][0].setImageResource(R.drawable.y);
        ltrs[2][1].setImageResource(R.drawable.o);
        ltrs[2][2].setImageResource(R.drawable.u);

        ltrs[2][5].setImageResource(R.drawable.w);
        ltrs[2][6].setImageResource(R.drawable.i);
        ltrs[2][7].setImageResource(R.drawable.n);

        LockAllTheButtons();

        //score = score*2;

        Log.i(TAG, "Start:win-switch.");
        switch (diff)
        {
            case "easy":
                Log.i(TAG, "Res:easy");
                score = score + 75;
                if (lives == 7)
                    score = score + 100;
                break;
            case "normal":
                Log.i(TAG, "Res:normal");
                score = score + 125;
                if (lives == 7)
                    score = score + 250;
                break;
            case "hard":
                Log.i(TAG, "Res:hard");
                score = score + 200;
                if (lives == 7)
                    score = score + 500;
                break;
            default:
                Log.i(TAG, "Res:wtf-win-------------------WTF_HELP---");
                //Toast.makeText(this, "WTF - win", Toast.LENGTH_LONG).show();
                break;
        }
        Log.i(TAG, "End:win-switch.");

        GlobalVariables globalvariables=(GlobalVariables)getApplication();

        globalvariables.setScore(Long.toString(score));

        scoreview.setText(Long.toString(score));

        Log.i(TAG, "Start:Ressetting the loosing streak.");
        streak = 0;
        Log.i(TAG, "End:Ressetting the loosing streak.");
        Log.i(TAG, "Start:Saving the score.");
        SaveScore();
        Log.i(TAG, "End:Saving the score.");
    }

    public void DefeatTxt ()
    {

        ltrs[2][0].setImageResource(R.drawable.y);
        ltrs[2][1].setImageResource(R.drawable.o);
        ltrs[2][2].setImageResource(R.drawable.u);

        ltrs[2][4].setImageResource(R.drawable.l);
        ltrs[2][5].setImageResource(R.drawable.o);
        ltrs[2][6].setImageResource(R.drawable.s);
        ltrs[2][7].setImageResource(R.drawable.e);

        LockAllTheButtons();
        Log.i(TAG, "Start:fail-switch.");
        switch (diff)
        {
            case "easy":
                Log.i(TAG, "Res:easy");
                score = score - 35;
                break;
            case "normal":
                Log.i(TAG, "Res:normal");
                score = score - 55;
                break;
            case "hard":
                Log.i(TAG, "Res:hard");
                score = score - 75;
                break;
            default:
                Log.i(TAG, "Res:wtf-fail");
                //Toast.makeText(this, "WTF - fail", Toast.LENGTH_LONG).show();
                break;
        }
        Log.i(TAG, "End:fail-switch.");

        Log.i(TAG,"Start:checking loosing streak.");

        streak++;
        if (streak == 7) // changed from 3 to 7 - #balancing
        {
            score=0;

            GlobalVariables globalvariables=(GlobalVariables)getApplication();

            globalvariables.setScore(Long.toString(score));

            scoreview.setText(Long.toString(score));
        }
        else
        {
            //score = score - score/6;

            GlobalVariables globalvariables=(GlobalVariables)getApplication();

            globalvariables.setScore(Long.toString(score));

            scoreview.setText(Long.toString(score));
        }

        Log.i(TAG,"End:checking loosing streak.");

        Log.i(TAG, "Start:Saving the score.");
        SaveScore();
        Log.i(TAG, "End:Saving the score.");

        Toast.makeText(this, "You were suppose to guess this word : " + guessThisWord, Toast.LENGTH_LONG).show();
    }
    //----//
    public class ResetAll implements Runnable
    {
        @Override
        public void run ()
        {
            Log.i(TAG,"Start: Full reset.");
            //---------------------------//
            letMeGo = false;
            for (int i = 0; i < 3 ; i++)
                for (int q = 0 ; q < 8 ; q++)
                {

                    String STRemptyplaceholder = "emptyplaceholder";
                    int resID = getResources().getIdentifier(STRemptyplaceholder, "drawable", getPackageName());
                    ltrs[i][q].setBackgroundResource(resID); // these are imageviews, if requested I will be back to finish this

                }
            Log.i(TAG,"Finished: Reset LetterViews pair images - Background Resource.");
                letMeGo = true;
            //---------------------------//
            Log.i(TAG, "Start: Reset HangMan image.");
            hangman.setImageResource(R.drawable.emptyplaceholder);
            Log.i(TAG,"Finished: Reset HangMan image.");
            //---------------------------//
                for (int i = 0; i < 26 ; i++)
                    try
                    {
                        Log.i(TAG, "buttonID " + i);
                        int resID = getResources().getIdentifier(Character.toString(abcs.toCharArray()[i]), "drawable", getPackageName()); // this took longer than it was soppose to...
                        buttons[i].setBackgroundResource(resID);
                    }
                    catch (Exception e)
                    {
                        Log.i(TAG,"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        i--;
                    }
            Log.i(TAG,"Finished: Reset buttons pair images.");
            //---------------------------//
            //---------------------------//
            Log.i(TAG,"Start: Change buttons to clickable.");
            for (int i = 0 ; i < 26 ; i++)
                buttons[i].setClickable(true);
            Log.i(TAG,"Finished: Change buttons to clickable.");
            //---------------------------//
            Log.i(TAG,"Finished: Full reset.");
        }
    }
    //----//
    //----//




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


