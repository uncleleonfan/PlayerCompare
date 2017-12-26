package com.leon.playercompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartIjkPlayer(View view) {
        Intent intent = new Intent(this, IjkPlayerActivity.class);
        startActivity(intent);
    }

    public void onStartPLDroidPlayer(View view) {
        Intent intent = new Intent(this, PLDroidPlayerActivity.class);
        startActivity(intent);

    }

    public void onStartVitamioPlayer(View view) {
        Intent intent = new Intent(this, VitamioPlayerActivity.class);
        startActivity(intent);
    }
}
