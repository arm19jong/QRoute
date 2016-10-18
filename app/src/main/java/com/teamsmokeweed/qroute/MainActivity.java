package com.teamsmokeweed.qroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamsmokeweed.qroute.database.GenRecycleview;
import com.teamsmokeweed.qroute.database.ViewGenQr;
import com.teamsmokeweed.qroute.genqr.GenQrActivity;
import com.teamsmokeweed.qroute.readqr.ReadActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonToGen, buttonToRead, buttonToRoute;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        buttonToGen = (Button) findViewById(R.id.ButtonToGen);
        buttonToGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GenQrActivity.class);
                //i.putExtra("sendTextStr",sendText.getText().toString() );
                startActivity(i);
            }
        });

        buttonToRead = (Button) findViewById(R.id.ButtonToRead);
        buttonToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReadActivity.class);
                //i.putExtra("sendTextStr",sendText.getText().toString() );
                startActivity(i);
            }
        });

        buttonToRoute = (Button) findViewById(R.id.ButtonToRoute);
        buttonToRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GenRecycleview.class);
                //i.putExtra("sendTextStr",sendText.getText().toString() );
                startActivity(i);
            }
        });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
