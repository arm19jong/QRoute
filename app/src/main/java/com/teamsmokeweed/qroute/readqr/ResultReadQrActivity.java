package com.teamsmokeweed.qroute.readqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.otto.Subscribe;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.RouteMap;
import com.teamsmokeweed.qroute.bar.App;
import com.teamsmokeweed.qroute.bar.BlackButtonClicked;
import com.teamsmokeweed.qroute.database.AddDatabaseQr;
import com.teamsmokeweed.qroute.genqr.Contents;
import com.teamsmokeweed.qroute.genqr.DateQr;
import com.teamsmokeweed.qroute.genqr.GenQr2Activity;
import com.teamsmokeweed.qroute.genqr.QRCodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jongzazaal on 15/10/2559.
 */

public class ResultReadQrActivity  extends AppCompatActivity {


    private Button route, reGenQr;
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
        reGenQr = (Button) findViewById(R.id.reGenQr);


        Intent i = getIntent();
        sQr = i.getStringArrayExtra("sQr");

        setDateQr();
        AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
        addDatabaseQr.addDb();
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

        reGenQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;


                String sQr = dateQr.getsQr();
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(sQr,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);

                Bitmap bitmap = null;
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                    //imgQrGen.setImageBitmap(bitmap);
                    //AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
                    //addDatabaseQr.addDb();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent i = new Intent(getApplicationContext(), GenQr2Activity.class);
                i.putExtra("img",byteArray);
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
