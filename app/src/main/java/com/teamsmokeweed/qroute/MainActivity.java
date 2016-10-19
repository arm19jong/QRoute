package com.teamsmokeweed.qroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.teamsmokeweed.qroute.database.GenRecycleviewActivity;
import com.teamsmokeweed.qroute.genqr.GenQrActivity;
import com.teamsmokeweed.qroute.readqr.ReadActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonToGen, buttonToRead, buttonToRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


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
                Intent i = new Intent(getApplicationContext(), GenRecycleviewActivity.class);
                //i.putExtra("sendTextStr",sendText.getText().toString() );
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        return true;
    }
}
