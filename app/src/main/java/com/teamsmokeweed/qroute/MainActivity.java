package com.teamsmokeweed.qroute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teamsmokeweed.qroute.genqr.GenQrActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonToGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonToGen = (Button) findViewById(R.id.ButtonToGen);
        buttonToGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GenQrActivity.class);
                //i.putExtra("sendTextStr",sendText.getText().toString() );
                startActivity(i);
            }
        });
    }
}
