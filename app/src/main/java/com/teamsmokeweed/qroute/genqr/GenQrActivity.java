package com.teamsmokeweed.qroute.genqr;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.teamsmokeweed.qroute.R;

/**
 * Created by jongzazaal on 14/10/2559.
 */

public class GenQrActivity extends AppCompatActivity {

    private Button buttonGen;
    private EditText titles, placeName, placeType, des, latLng, webPage;
    private ImageView imgQrGen;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_qr);

        buttonGen = (Button) findViewById(R.id.buttonGen);
        titles = (EditText) findViewById(R.id.titles);
        placeName = (EditText) findViewById(R.id.placeName);
        placeType = (EditText) findViewById(R.id.placeType);
        des = (EditText) findViewById(R.id.des);
        latLng = (EditText) findViewById(R.id.latLng);
        webPage = (EditText) findViewById(R.id.webPage);
        imgQrGen = (ImageView) findViewById(R.id.imgQrGen);

        final DateQr dateQr = new DateQr();

        buttonGen.setOnClickListener(new View.OnClickListener() {
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

                dateQr.setTitles(titles.getText().toString());
                dateQr.setPlaceName(placeName.getText().toString());
                dateQr.setPlaceType(placeType.getText().toString());
                dateQr.setDes(des.getText().toString());
                String[] sLatLng = latLng.getText().toString().split(",");
                dateQr.setLat(Float.valueOf(sLatLng[0]));
                dateQr.setLng(Float.valueOf(sLatLng[1]));
                dateQr.setWebPage(webPage.getText().toString());

                String sQr = dateQr.getsQr();
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(sQr,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);

                Bitmap bitmap = null;
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                    imgQrGen.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }



            }
        });
    }
}
