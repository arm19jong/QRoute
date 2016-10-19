package com.teamsmokeweed.qroute.genqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.teamsmokeweed.qroute.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jongzazaal on 19/10/2559.
 */

public class GenQr2Activity extends AppCompatActivity {

    ImageView imageView;
    Bitmap bitmap;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_qr2);

        imageView = (ImageView) findViewById(R.id.imgQrGen);
        saveButton = (Button) findViewById(R.id.save);

        Intent i = getIntent();
        byte[] byteArray = getIntent().getByteArrayExtra("img");
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Save bitmap to storage
                    Date d = new Date();
                    String filename  = (String) DateFormat.format("hhmmss-MMddyyyy"
                            , d.getTime());
                    File dir = new File(Environment.getExternalStorageDirectory()
                            , "/Pictures/" + filename + ".png");
                    FileOutputStream out = new FileOutputStream(dir);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    out.write(bos.toByteArray());

                    // Clear bitmap
                    bitmap.recycle();

                    // Update image to media system
                    Intent intent =
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(dir));
                    sendBroadcast(intent);

                    Toast.makeText(getApplicationContext(), "Saved!"
                            , Toast.LENGTH_SHORT).show();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
