package com.szabhun.sgttteam.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoresActivity extends AppCompatActivity {

    public float score = 0;

    LinearLayout MLayouts;

    TextView src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

    }
}
