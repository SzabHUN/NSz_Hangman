package com.szabhun.sgttteam.hangman;

import android.app.Application;

public class GlobalVariables extends Application {

    private String languagecode;
    private String opt_muteunmute;
    private String difficulty;
    private String opt_difficulty;
    private String score;
    private String scoreTable; // scores are stored in a string array, each separated by ' ; '

//-----------------------------------------------------//
public void setOpt_muteunmute(String opt_muteunmute) {
    this.opt_muteunmute = opt_muteunmute;
}

public String getOpt_muteunmute() {
    return opt_muteunmute;
}
//-----------------------------------------------------//
public void setLanguagecode(String languagecode) {
    this.languagecode = languagecode;
}

public String getLanguagecode() {
    return languagecode;
}
//-----------------------------------------------------//
public void setDifficulty(String difficulty)
{
    this.difficulty = difficulty;
}

public String getDifficulty()
{
    return difficulty;
}
//-----------------------------------------------------//
public void setScore(String score)
{
    this.score = score;
}

public String getScore()
{
    return score;
}
//-----------------------------------------------------//
public String getOpt_difficulty() {
    return opt_difficulty;
}

public void setOpt_difficulty(String opt_difficulty) {
    this.opt_difficulty = opt_difficulty;
}
//-----------------------------------------------------//
public void setScoreTable(String scoreTable)
{
    this.scoreTable = scoreTable;
}

public String getScoreTable()
{
    return scoreTable;
}
//-----------------------------------------------------//

}
