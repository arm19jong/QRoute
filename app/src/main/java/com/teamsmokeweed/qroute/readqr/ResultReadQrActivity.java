package com.teamsmokeweed.qroute.readqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.RouteMap;
import com.teamsmokeweed.qroute.genqr.DateQr;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jongzazaal on 15/10/2559.
 */

public class ResultReadQrActivity  extends AppCompatActivity {


    private Button route;
    private TextView titles, placeName, placeType, des, latLng, webPage;
    String[] sQr;
    DateQr dateQr;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.result_read_qr);

        titles = (TextView) findViewById(R.id.titles_ans);
        placeName = (TextView) findViewById(R.id.placeName_ans);
        placeType = (TextView) findViewById(R.id.placeType_ans);
        des = (TextView) findViewById(R.id.des_ans);
        latLng = (TextView) findViewById(R.id.latLng_ans);
        webPage = (TextView) findViewById(R.id.webPage_ans);
        route = (Button) findViewById(R.id.route);


        Intent i = getIntent();
        sQr = i.getStringArrayExtra("sQr");

        setDateQr();
        showData();

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ist<Float> latLng = Arrays.asList(dateQr.getLat(), dateQr.getLng());
                Intent i = new Intent(getApplicationContext(), RouteMap.class);
                i.putExtra("lat", dateQr.getLat());
                i.putExtra("lng", dateQr.getLng());
                startActivity(i);
            }
        });

    }

    private void setDateQr(){
        dateQr = new DateQr();

        dateQr.setTitles(sQr[2]);
        dateQr.setPlaceName(sQr[3]);
        dateQr.setPlaceType(sQr[4]);
        dateQr.setDes(sQr[5]);
        dateQr.setLat(Float.valueOf(sQr[0]));
        dateQr.setLng(Float.valueOf(sQr[1]));
        dateQr.setWebPage(sQr[6]);
    }

    private void showData(){
        titles.setText(dateQr.getTitles());
        placeName.setText(dateQr.getPlaceName());
        placeType.setText(dateQr.getPlaceType());
        des.setText(dateQr.getDes());
        latLng.setText(dateQr.getLat()+", "+dateQr.getLng());
        webPage.setText(dateQr.getWebPage());
    }


}
